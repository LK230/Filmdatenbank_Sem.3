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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    public Review createReview(String reviewBody, String rating, String imdbId, String username) {
        logger.info("Creating review for movie with ID: " + imdbId + " by user: " + username);
        try {
            Optional<User> currentUser = userRepository.findByUsernameIgnoreCase(username);
            Review review = reviewRepository.insert(new Review(reviewBody, rating, imdbId, LocalDateTime.now(), LocalDateTime.now(), currentUser.get().getUsername()));
            mongoTemplate.update(Movie.class)
                    .matching(Criteria.where("imdbId").is(imdbId))
                    .apply(new Update()
                            .push("reviewIds").value(review)
                            .inc("reviews", 1))
                    .first();
            currentUser.get().getProfile().addReview(review);
            userRepository.save(currentUser.get());
            userProfileRepository.save(currentUser.get().getProfile());
            return review;
        } catch (NoSuchElementException e) {
            logger.error("No user or movie found with given username or id");
            return null;
        }
    }

    public boolean deleteReview(String id) {
        logger.info("Deleting review with ID: " + id);
        try {
            ObjectId reviewId = new ObjectId(id);
            Review review = reviewRepository.findById(reviewId).orElse(null);
            if(review != null) {
                User user = userRepository.findByUsernameIgnoreCase(review.getCreatedBy()).orElse(null);
                user.getProfile().getReviews().removeIf(reviewToRemove -> review.getId().equals((reviewId)));
                userRepository.save(user);
                userProfileRepository.save(user.getProfile());
                Movie movie = movieRepository.findMovieByImdbId(review.getImdbId()).orElse(null);
                mongoTemplate.update(Movie.class)
                        .matching(Criteria.where("imdbId").is(movie.getImdbId()))
                        .apply(new Update()
                                .pull("reviewIds", review.getId())
                                .inc("reviews", -1))
                        .first();
                reviewRepository.delete(review);
                return true;
            }
        }catch(NullPointerException e) {
            logger.error("Review not found");
            return false;
        }
        catch(IllegalArgumentException e) {
            logger.error("Could not convert given string to objectID");
            return false;
        }
        return false;
    }

    public boolean updateReview(String id, String body, String rating) {
        logger.info("Updating review with ID: " + id);
        try {
            ObjectId reviewId = new ObjectId(id);
            Review review = reviewRepository.findById(reviewId).orElse(null);
            if(review != null) {
                review.setBody(body);
                review.setRating(rating);
                review.setUpdated(LocalDateTime.now());
                reviewRepository.save(review);
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
        } catch(IllegalArgumentException e) {
            logger.error("Could not convert given string to objectID");
            return false;
        }
    }
}