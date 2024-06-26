package mi.filmdatenprofis.movieapp.controller;

import mi.filmdatenprofis.movieapp.model.Review;
import mi.filmdatenprofis.movieapp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Allow cross-origin requests from any domain
@CrossOrigin(origins = "*")
// Mark this class as a REST controller to handle HTTP requests
@RestController
// Base URL mapping for all methods in this controller
@RequestMapping("/reviews")
public class ReviewController {

    // Inject the ReviewService dependency
    @Autowired
    private ReviewService reviewService;

    // Logger for logging information and error messages
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    // Endpoint to create a new review
    @PostMapping("/create")
    public ResponseEntity<?> createReview(@RequestParam String reviewBody, @RequestParam(required = false) Integer rating, @RequestParam String imdbId, @RequestParam String username) {

        // Check if rating is provided and is within valid range
        if(rating != null && (rating < 1 || rating > 5)) {
            logger.error("Invalid rating value provided");
            return new ResponseEntity<String>("An error occurred creating the review (Rating too high or too low)", HttpStatus.BAD_REQUEST);
        }

        // Log the creation of the review
        logger.info("Creating review for movie with ID: " + imdbId);
        // Create the review using the review service
        Review review = reviewService.createReview(reviewBody, rating, imdbId, username);

        // Return the created review or an error message if creation failed
        if(review != null) {
            return new ResponseEntity<Review>(review, HttpStatus.OK);
        } else {
            logger.error("Error occurred creating the review");
            return new ResponseEntity<String>("An error occurred creating the review", HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to delete an existing review
    @DeleteMapping("/remove")
    public ResponseEntity<String> deleteReview(@RequestParam String reviewId) {
        // Log the deletion of the review
        logger.info("Deleting review with ID: " + reviewId);
        // Attempt to delete the review using the review service
        if(reviewService.deleteReview(reviewId)) {
            return new ResponseEntity<String>("Review was deleted", HttpStatus.OK);
        } else {
            logger.error("Error occurred removing the review");
            return new ResponseEntity<String>("An error occurred removing the review", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to update an existing review
    @PatchMapping("/update")
    public ResponseEntity<String> updateReview(@RequestParam String reviewId, @RequestParam String body, @RequestParam Integer rating) {
        // Log the update of the review
        logger.info("Updating review with ID: " + reviewId);
        // Attempt to update the review using the review service
        if(reviewService.updateReview(reviewId, body, rating)) {
            return new ResponseEntity<String>("Review was updated successfully", HttpStatus.OK);
        } else {
            logger.error("Error occurred updating the review");
            return new ResponseEntity<String>("An error occurred updating the review", HttpStatus.NOT_FOUND);
        }
    }
}