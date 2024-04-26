package mi.filmdatenprofis.movieapp.controller;

import mi.filmdatenprofis.movieapp.model.Review;
import mi.filmdatenprofis.movieapp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    //Endpoint to create a new Review
    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> payload) {

        Review newReview = reviewService.createReview(payload.get("reviewBody"), payload.get("imdbId"), payload.get("username"));

        return new ResponseEntity<Review>(newReview, HttpStatus.CREATED);

    }

}
