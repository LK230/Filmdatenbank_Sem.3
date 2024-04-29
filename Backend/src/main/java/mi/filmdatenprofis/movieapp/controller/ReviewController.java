package mi.filmdatenprofis.movieapp.controller;

// Importing necessary libraries and classes
import mi.filmdatenprofis.movieapp.model.Review;
import mi.filmdatenprofis.movieapp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// Annotation to allow cross-origin requests
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> review) {
        return new ResponseEntity<Review>(reviewService.createReview(review.get("body"), review.get("rating"), review.get("imdbId"), review.get("username")), HttpStatus.OK);
    }
}



