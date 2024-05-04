package mi.filmdatenprofis.movieapp.service;

import mi.filmdatenprofis.movieapp.model.Movie;
import mi.filmdatenprofis.movieapp.model.Review;
import mi.filmdatenprofis.movieapp.model.User;
import mi.filmdatenprofis.movieapp.repository.ReviewRepository;
import mi.filmdatenprofis.movieapp.repository.UserProfileRepository;
import mi.filmdatenprofis.movieapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;


    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(String reviewBody, String rating, String imdbId, String username) {

        try {
            // Find the current user by their username
            Optional<User> currentUser = userRepository.findByUsername(username);

            // Create the review and insert it into the review repository
            Review review = reviewRepository.insert(new Review(reviewBody, rating, imdbId, LocalDateTime.now(), LocalDateTime.now(), currentUser.get().getUsername()));

            // Update the movie document in the database to include the new review ID
            mongoTemplate.update(Movie.class)
                    .matching(Criteria.where("imdbId").is(imdbId))
                    .apply(new Update().push("reviewIds").value(review))
                    .first();

            // Add the review to the current user's list of reviews and save the user
            currentUser.get().getProfile().addReview(review);
            userRepository.save(currentUser.get());
            userProfileRepository.save(currentUser.get().getProfile());
            System.out.println(review.getBody());
            return review;

        } catch (NoSuchElementException e) {
            // Handle the case where no user is found with the given username
            System.out.println("No username found with username:" + username);
            return null;
        }
    }
}
