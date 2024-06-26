package mi.filmdatenprofis.movieapp.controller;

import mi.filmdatenprofis.movieapp.model.Movie;
import mi.filmdatenprofis.movieapp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

// Allows cross-origin requests from any domain
@CrossOrigin(origins = "*")
// Indicates that this class is a REST controller
@RestController
// Maps HTTP requests to /movies URL
@RequestMapping("/movies")
public class MovieController {

    // Automatically injects an instance of MovieService
    @Autowired
    private MovieService movieService;

    // Logger instance to log messages
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    // Handles HTTP GET requests to /movies, returns a list of all movies
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        logger.info("Getting all movies");
        // Returns the list of all movies with an HTTP status of OK (200)
        return new ResponseEntity<List<Movie>>(movieService.allMovies(), HttpStatus.OK);
    }

    // Handles HTTP GET requests to /movies/{imdbId}, returns a single movie by its IMDb ID
    @GetMapping("/{imdbId}")
    public ResponseEntity<Optional<Movie>> getSingleMovie(@PathVariable String imdbId) {
        logger.info("Getting movie with ID: " + imdbId);
        // Returns the movie with the specified IMDb ID with an HTTP status of OK (200)
        return new ResponseEntity<Optional<Movie>>(movieService.singleMovie(imdbId), HttpStatus.OK);
    }

    // Handles HTTP GET requests to /movies/title/{title}, returns movies matching the given title
    @GetMapping("/title/{title}")
    public ResponseEntity<List<Movie>> findMoviesByTitle(@PathVariable String title) {
        logger.info("Finding movies with title: " + title);
        // Returns a list of movies with the specified title with an HTTP status of OK (200)
        return new ResponseEntity<List<Movie>>(movieService.findMoviesByTitle(title), HttpStatus.OK);
    }

    // Handles HTTP GET requests to /movies/genre, returns movies categorized by genre
    @GetMapping("/genre")
    public Map<String, List<Movie>> getMoviesByGenre() {
        logger.info("Getting movies by genre");
        // Returns a map of genres to lists of movies
        return movieService.getMoviesByGenre();
    }

    // Handles HTTP GET requests to /movies/genre/{genre}, returns movies of a specific genre
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Movie>> findMoviesByGenre(@PathVariable String genre) {
        logger.info("Finding movies with genre: " + genre);
        // Returns a list of movies in the specified genre with an HTTP status of OK (200)
        return new ResponseEntity<List<Movie>>(movieService.findMoviesByGenre(genre), HttpStatus.OK);
    }

    // Handles HTTP GET requests to /movies/newest, returns the newest released movies
    @GetMapping("/newest")
    public ResponseEntity<List<Movie>> findNewestMovies() {
        logger.info("Finding newest movies");
        // Returns a list of the newest movies sorted by release date with an HTTP status of OK (200)
        return new ResponseEntity<List<Movie>>(movieService.allMoviesSortedByReleaseDate(), HttpStatus.OK);
    }

    // Handles HTTP GET requests to /movies/director/{director}, returns movies by a specific director
    @GetMapping("/director/{director}")
    public ResponseEntity<List<Movie>> findMoviesByDirector(@PathVariable String director) {
        logger.info("Finding movies by director: " + director);
        // Returns a list of movies directed by the specified director with an HTTP status of OK (200)
        return new ResponseEntity<List<Movie>>(movieService.findMoviesByDirector(director), HttpStatus.OK);
    }

    // Handles HTTP GET requests to /movies/bestrated, returns the best-rated movies
    @GetMapping("/bestrated")
    public ResponseEntity<List<Movie>> findBestMovies() {
        logger.info("Finding best rated movies");
        // Returns a list of the best-rated movies sorted by rating with an HTTP status of OK (200)
        return new ResponseEntity<List<Movie>>(movieService.allMoviesSortedByRating(), HttpStatus.OK);
    }
}