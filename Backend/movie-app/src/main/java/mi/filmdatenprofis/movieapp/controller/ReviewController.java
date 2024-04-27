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
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping()
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> payload) {

        return new ResponseEntity<Review>(reviewService.createReview(payload.get("reviewBody"), Integer.parseInt(payload.get("reviewRating")), payload.get("imdbId")), HttpStatus.OK);
    }

    /*
    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        return new ResponseEntity<Review>(reviewService.createReview(review), HttpStatus.CREATED);
    }

     */

    /*
    @PostMapping("/{imdbId}/review")
    public ResponseEntity<Review> reviewMovie(@PathVariable String imdbId, @RequestBody Review review) {
        return new ResponseEntity<Review>(reviewService.reviewMovie(imdbId, review), HttpStatus.CREATED);
    }
     */
}


