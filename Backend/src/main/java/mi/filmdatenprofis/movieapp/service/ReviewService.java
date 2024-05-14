package mi.filmdatenprofis.movieapp.service;

import mi.filmdatenprofis.movieapp.model.Movie;
import mi.filmdatenprofis.movieapp.model.Review;
import mi.filmdatenprofis.movieapp.model.User;
import mi.filmdatenprofis.movieapp.model.UserProfile;
import mi.filmdatenprofis.movieapp.repository.MovieRepository;
import mi.filmdatenprofis.movieapp.repository.ReviewRepository;
import mi.filmdatenprofis.movieapp.repository.UserProfileRepository;
import mi.filmdatenprofis.movieapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.List;
import java.util.stream.IntStream;

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


    //method to create a new review
    public Review createReview(String reviewBody, String rating, String imdbId, String username) {

        try {
            // Find the current user by their username
            Optional<User> currentUser = userRepository.findByUsernameIgnoreCase(username);

            // Create the review and insert it into the review repository
            Review review = reviewRepository.insert(new Review(reviewBody, rating, imdbId, LocalDateTime.now(), LocalDateTime.now(), currentUser.get().getUsername()));

            // Update the movie document in the database to include the new review ID
            mongoTemplate.update(Movie.class)
                    .matching(Criteria.where("imdbId").is(imdbId))
                    .apply(new Update()
                            .push("reviewIds").value(review)
                            .inc("reviews", 1))
                    .first();

            // Add the review to the current user's list of reviews and save the user
            currentUser.get().getProfile().addReview(review);
            userRepository.save(currentUser.get());
            userProfileRepository.save(currentUser.get().getProfile());
            return review;

        } catch (NoSuchElementException e) {
            // Handle the case where no user is found with the given username
            System.out.println("No user or movie found with given username or id");
            return null;
        }
    }

    //method to delete a review
    public boolean deleteReview(String id) {

            try {

                //Convert string review id to an ObjectId and search review by ID
                ObjectId reviewId = new ObjectId(id);
                Review review = reviewRepository.findById(reviewId).orElse(null);

                //If review found delete it
                if(review != null) {

                    //Delete review from user and userprofile collection and save changed files to database
                    User user = userRepository.findByUsernameIgnoreCase(review.getCreatedBy()).orElse(null);
                    user.getProfile().getReviews().removeIf(reviewToRemove -> review.getId().equals((reviewId)));
                    userRepository.save(user);
                    userProfileRepository.save(user.getProfile());

                    //Delete review from movie collection and save changed files to database
                    Movie movie = movieRepository.findMovieByImdbId(review.getImdbId()).orElse(null);
                    mongoTemplate.update(Movie.class)
                            .matching(Criteria.where("imdbId").is(movie.getImdbId()))
                            .apply(new Update()
                                    .pull("reviewIds", review.getId())
                                    .inc("reviews", -1))
                            .first();

                    //Delete review from review collection in database
                    reviewRepository.delete(review);

                    return true;
                }

            //Handle exception when review was not found
            }catch(NullPointerException e) {
                System.out.println("Review not found");
                return false;
            }
            //Handle exception when given string id could not be converted into ObjectID
            catch(IllegalArgumentException e) {
                System.out.println("Could not convert given string to objectID");
                return false;
            }

        return false;
    }

    //method to update an existing review
    public boolean updateReview(String id, String body, String rating) {

        try {
            //Convert string review id to an ObjectId
            ObjectId reviewId = new ObjectId(id);

            //Find review with given id
            Review review = reviewRepository.findById(reviewId).orElse(null);

            //If review found set body, rating and updated to new values
            if(review != null) {

                //Update review in reviews
                review.setBody(body);
                review.setRating(rating);
                review.setUpdated(LocalDateTime.now());
                reviewRepository.save(review);

                // Update review in user profile
                User user = userRepository.findByUsernameIgnoreCase(review.getCreatedBy()).orElse(null);
                if (user != null) {
                    List<Review> reviews = user.getProfile().getReviews();
                    reviews.stream()
                            .filter(userProfileReview -> userProfileReview.getId().equals(review.getId()))
                            .findFirst()
                            .ifPresent(userProfileReview -> {
                                userProfileReview.setRating(rating);
                                userProfileReview.setBody(body);
                            });
                    userProfileRepository.save(user.getProfile());
                    userRepository.save(user);
                }

                // Update review in movie
                Movie movie = movieRepository.findMovieByImdbId(review.getImdbId()).orElse(null);
                if (movie != null) {
                    List<Review> reviews = movie.getReviewIds();
                    int index = IntStream.range(0, reviews.size())
                            .filter(i -> reviews.get(i).getId().equals(review.getId()))
                            .findFirst()
                            .orElse(-1);
                    if (index != -1) {
                        Review updatedReview = reviews.get(index);
                        updatedReview.setRating(rating);
                        updatedReview.setBody(body);
                        mongoTemplate.update(Movie.class)
                                .matching(Criteria.where("imdbId").is(movie.getImdbId()))
                                .apply(new Update().set("reviewIds[" + index + "]", updatedReview))
                                .first();
                    }
                }

                return true;
            }
            else {
                return false;
            }
        //Catch exception if given id string could not be converted into an objectid
        } catch(IllegalArgumentException e) {
            System.out.println("Could not convert given string to objectID");
            return false;
        }

    }
}
