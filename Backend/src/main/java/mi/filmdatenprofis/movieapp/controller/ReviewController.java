package mi.filmdatenprofis.movieapp.controller;

// Importing necessary libraries and classes
import mi.filmdatenprofis.movieapp.model.Review;
import mi.filmdatenprofis.movieapp.service.ReviewService;
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
    public ResponseEntity<Review> createReview(@RequestParam String reviewBody, @RequestParam String rating, @RequestParam String imdbId, @RequestParam String username) {
        return new ResponseEntity<Review>(reviewService.createReview(reviewBody, rating, imdbId, username), HttpStatus.OK);
    }
}



