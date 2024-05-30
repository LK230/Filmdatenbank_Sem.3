package mi.filmdatenprofis.movieapp.controller;

import mi.filmdatenprofis.movieapp.model.Review;
import mi.filmdatenprofis.movieapp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @PostMapping("/create")
    public ResponseEntity<?> createReview(@RequestParam String reviewBody, @RequestParam String rating, @RequestParam String imdbId, @RequestParam String username) {
        logger.info("Creating review for movie with ID: " + imdbId);
        Review review = reviewService.createReview(reviewBody, rating, imdbId, username);

        if(review != null) {
            return new ResponseEntity<Review>(review, HttpStatus.OK);
        } else {
            logger.error("Error occurred creating the review");
            return new ResponseEntity<String>("An error occurred creating the review", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> deleteReview(@RequestParam String reviewId) {
        logger.info("Deleting review with ID: " + reviewId);
        if(reviewService.deleteReview(reviewId)) {
            return new ResponseEntity<String>("Review was deleted", HttpStatus.OK);
        } else {
            logger.error("Error occurred removing the review");
            return new ResponseEntity<String>("An error occurred removing the review", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateReview(@RequestParam String reviewId, @RequestParam String body, @RequestParam String rating) {
        logger.info("Updating review with ID: " + reviewId);
        if(reviewService.updateReview(reviewId, body, rating)) {
            return new ResponseEntity<String>("Review was updated successfully", HttpStatus.OK);
        } else {
            logger.error("Error occurred updating the review");
            return new ResponseEntity<String>("An error occurred updating the review", HttpStatus.NOT_FOUND);
        }
    }
}