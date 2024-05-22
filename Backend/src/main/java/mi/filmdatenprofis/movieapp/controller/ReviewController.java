package mi.filmdatenprofis.movieapp.controller;

// Importing necessary libraries and classes
import mi.filmdatenprofis.movieapp.model.Review;
import mi.filmdatenprofis.movieapp.service.ReviewService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// Annotation to allow cross-origin requests
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    // Method to handle POST requests at the endpoint "/create" which creates a new review and returns it
    @PostMapping("/create")
    public ResponseEntity<?> createReview(@RequestParam String reviewBody, @RequestParam String rating, @RequestParam String imdbId, @RequestParam String username) {
        Review review = reviewService.createReview(reviewBody, rating, imdbId, username);

        if(review != null) {
            return new ResponseEntity<Review>(review, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("An error occured creating the review", HttpStatus.BAD_REQUEST);
        }

    }

    // Method to handle DELETE requests at the endpoint "/delete" which deletes a review
    @DeleteMapping("/remove")
    public ResponseEntity<String> deleteReview(@RequestParam String reviewId) {
        if(reviewService.deleteReview(reviewId)) {
            return new ResponseEntity<String>("Review was deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("An error occured removing the review", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Method to handle PATCH requests at the endpoint "/update" which updates an existing review
    @PatchMapping("/update")
    public ResponseEntity<String> updateReview(@RequestParam String reviewId, @RequestParam String body, @RequestParam String rating) {
        if(reviewService.updateReview(reviewId, body, rating)) {
            return new ResponseEntity<String>("Review was updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("An error occured updating the review", HttpStatus.NOT_FOUND);
        }
    }
}



