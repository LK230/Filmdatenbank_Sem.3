package mi.filmdatenprofis.movieapp.service;

import mi.filmdatenprofis.movieapp.model.Movie;
import mi.filmdatenprofis.movieapp.model.Review;
import mi.filmdatenprofis.movieapp.model.User;
import mi.filmdatenprofis.movieapp.repository.MovieRepository;
import mi.filmdatenprofis.movieapp.repository.ReviewRepository;
import mi.filmdatenprofis.movieapp.repository.UserProfileRepository;
import mi.filmdatenprofis.movieapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Service class for managing reviews and calculating movie ratings.
 */
@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    /**
     * Checks if a user has already given a rating for a specific movie.
     *
     * This method iterates over all reviews and checks if there exists a review
     * with the given IMDb ID, created by the user with the provided email, and
     * has a non-null rating.
     *
     * @param imdbId the IMDb ID of the movie
     * @param email  the email of the user
     * @return true if the user has rated the movie, false otherwise
     */
    private boolean hasUserRatedMovie(String imdbId, String email) {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .anyMatch(review -> review.getImdbId().equals(imdbId) && review.getCreatedBy().equalsIgnoreCase(email) && review.getRating() != null);
    }

    /**
     * Calculates the average rating for a specific movie based on its reviews.
     *
     * This method retrieves the movie by its IMDb ID and sums up all the ratings
     * of its reviews. The average rating is calculated and rounded to the nearest
     * 0.5 steps.
     *
     * @param imdbId the IMDb ID of the movie
     * @return the average rating of the movie, or null if the movie is not found
     */
    public Double calculateAverageRating (String imdbId) {

        //Calculate average rating of movie
        Movie movie = movieRepository.findMovieByImdbId(imdbId).orElse(null);

        try {
            double averageRating;
            int reviewRatingSum = 0;
            int totalReviewAmount = movie.getReviews();

            if(totalReviewAmount == 0) {
                return 0.0;
            }

            //Sum up all review ratings
            for(Review reviewInList : movie.getReviewIds()) {
                reviewRatingSum += reviewInList.getRating();
            }

            //Divide sum of review ratings by total review amount
            averageRating = (double)reviewRatingSum / totalReviewAmount ;

            //Round result to 0.5 steps
            averageRating = Math.round(averageRating *2) / 2.0;
            return averageRating;

            }catch(NullPointerException e) {
            return null;
        }

    }

    /**
     * Creates a new review for a movie and updates its average rating.
     * @param reviewBody Content of the review
     * @param rating Rating given to the movie
     * @param imdbId IMDb ID of the movie being reviewed
     * @param email Email of the user creating the review
     * @return Created Review object, or null if user or movie is not found
     */
    public Review createReview(String reviewBody, Integer rating, String imdbId, String email) {

        logger.info("Creating review for movie with ID: " + imdbId + " by user: " + email);

        try {
            // Find user and insert review to database
            Optional<User> currentUser = userRepository.findByEmailIgnoreCase(email);

            // Check if the user has already given a rating for this movie
            if (rating != null && hasUserRatedMovie(imdbId, email)) {
                logger.error("User has already given a rating for this movie");
                return null;
            }

            Review review = reviewRepository.insert(new Review(reviewBody, rating, imdbId, LocalDateTime.now(), LocalDateTime.now(), currentUser.get().getUsername()));

            // Update movies and push new review to review list
            mongoTemplate.update(Movie.class)
                    .matching(Criteria.where("imdbId").is(imdbId))
                    .apply(new Update()
                            .push("reviewIds", review)
                            .inc("reviews", 1))
                    .first();

            // Change rating of movie if a rating was provided
            if (rating != null) {
                double averageRating = calculateAverageRating(imdbId);
                mongoTemplate.update(Movie.class)
                        .matching(Criteria.where("imdbId").is(imdbId))
                        .apply(new Update()
                                .set("rating", averageRating))
                        .first();
            }

            // Save changes to database
            currentUser.get().getProfile().addReview(review);
            userRepository.save(currentUser.get());
            userProfileRepository.save(currentUser.get().getProfile());
            return review;
        } catch (NoSuchElementException e) {
            logger.error("No user or movie found with given username or id");
            return null;
        } catch(NullPointerException e) {
            logger.error("An error occurred creating the review");
            return null;
        }
    }

    /**
     * Deletes a review from the system and updates the average rating of the movie.
     * @param username Username of the user who created the review
     * @param imdbId movie ID which belongs to the review
     * @return true if deletion is successful, false otherwise
     */
    public boolean deleteReview(String username, String imdbId) {
        logger.info("Deleting review from user: " + username);

        try {
            // Convert id to ObjectID and find it in database
            Review review = reviewRepository.findOneByImdbIdAndCreatedBy(imdbId, username);

                // Delete review from user profile and save changes
                User user = userRepository.findByUsernameIgnoreCase(review.getCreatedBy()).orElse(null);
                user.getProfile().getReviews().removeIf(reviewToRemove -> review.getId().equals((review.getId())));
                userRepository.save(user);
                userProfileRepository.save(user.getProfile());

                // Find movie which belongs to the review
                Movie movie = movieRepository.findMovieByImdbId(review.getImdbId()).orElse(null);

                // Create update on movie and delete review
                Update update = new Update()
                        .pull("reviewIds", Query.query(Criteria.where("_id").is(review.getId())))
                        .inc("reviews", -1);

                mongoTemplate.update(Movie.class)
                        .matching(Criteria.where("imdbId").is(movie.getImdbId()))
                        .apply(update)
                        .first();


                // Adjust new rating of movie without deleted review
                double averageRating = calculateAverageRating(movie.getImdbId());
                    mongoTemplate.update(Movie.class)
                            .matching(Criteria.where("imdbId").is(movie.getImdbId()))
                            .apply(new Update()
                                    .set("rating", averageRating))
                            .first();

                reviewRepository.delete(review);
                return true;

        }catch(NullPointerException e) {
            logger.error("Review not found");
            return false;
        }
    }

    /**
     * Updates an existing review with new content and rating, and updates the movie's average rating.
     * @param imdbId ID of the movie which belongs to the review
     * @param usermail Usermail of user who created the review
     * @param body New content of the review
     * @param rating New rating given to the movie
     * @return true if update is successful, false otherwise
     */
    public boolean updateReview(String usermail, String imdbId, String body, Integer rating) {
        logger.info("Updating review from user with mail: " + usermail);
        try {

            // Find user by given usermail who created review
            Optional<User> user = userRepository.findByEmailIgnoreCase(usermail);

            // If user was found get username and find review on given movie
            if(user.isPresent()) {

                String username = user.get().getUsername();
                Review review = reviewRepository.findOneByImdbIdAndCreatedBy(imdbId, username);

                // If review was found set attributes to new values
                if (review != null) {

                    review.setBody(body);
                    review.setRating(rating);
                    review.setUpdated(LocalDateTime.now());
                    reviewRepository.save(review);

                    // Change review in userProfile and save to DB
                    List<Review> reviews = user.get().getProfile().getReviews();
                    reviews.stream()
                            .filter(userProfileReview -> userProfileReview.getId().equals(review.getId()))
                            .findFirst()
                            .ifPresent(userProfileReview -> {
                                userProfileReview.setRating(rating);
                                userProfileReview.setBody(body);
                            });

                    userProfileRepository.save(user.get().getProfile());
                    userRepository.save(user.get());


                    // Find movie which belongs to the review
                    Movie movie = movieRepository.findMovieByImdbId(review.getImdbId()).orElse(null);

                    // If movie was found change review also in movie
                    if (movie != null) {
                        List<Review> movieReviews = movie.getReviewIds();
                        int index = IntStream.range(0, reviews.size())
                                .filter(i -> movieReviews.get(i).getId().equals(review.getId()))
                                .findFirst()
                                .orElse(-1);
                        if (index != -1) {
                            Review updatedReview = movieReviews.get(index);
                            updatedReview.setRating(rating);
                            updatedReview.setBody(body);

                            mongoTemplate.update(Movie.class)
                                    .matching(Criteria.where("imdbId").is(movie.getImdbId()).and("reviewIds._id").is(updatedReview.getId())) // Verwende hier die ID des zu aktualisierenden Reviews
                                    .apply(new Update().set("reviewIds.$", updatedReview)) // Hier wird das $-Operator verwendet, um das entsprechende Element zu identifizieren
                                    .first();

                            //Adjust new rating of movie
                            double averageRating = calculateAverageRating(movie.getImdbId());
                            mongoTemplate.update(Movie.class)
                                    .matching(Criteria.where("imdbId").is(movie.getImdbId()))
                                    .apply(new Update()
                                            .set("rating", averageRating))
                                    .first();
                        }
                    }
                    return true;
                }
            } else {
                return false;
            }
        } catch(NullPointerException e) {
            logger.error("Review not found");
            return false;
        }catch(Exception e) {
            logger.error("An unexpected error occurred: ", e);
            return false;
        }
        return false;
    }
}