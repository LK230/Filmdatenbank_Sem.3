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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        logger.info("Getting all movies");
        return new ResponseEntity<List<Movie>>(movieService.allMovies(), HttpStatus.OK);
    }

    @GetMapping("/{imdbId}")
    public ResponseEntity<Optional<Movie>> getSingleMovie(@PathVariable String imdbId) {
        logger.info("Getting movie with ID: " + imdbId);
        return new ResponseEntity<Optional<Movie>>(movieService.singleMovie(imdbId), HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Movie>> findMoviesByTitle(@PathVariable String title) {
        logger.info("Finding movies with title: " + title);
        return new ResponseEntity<List<Movie>>(movieService.findMoviesByTitle(title), HttpStatus.OK);
    }

    @GetMapping("/genre")
    public Map<String, List<Movie>> getMoviesByGenre() {
        logger.info("Getting movies by genre");
        return movieService.getMoviesByGenre();
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Movie>> findMoviesByGenre(@PathVariable String genre) {
        logger.info("Finding movies with genre: " + genre);
        return new ResponseEntity<List<Movie>>(movieService.findMoviesByGenre(genre), HttpStatus.OK);
    }

    @GetMapping("/newest")
    public ResponseEntity<List<Movie>> findNewestMovies() {
        logger.info("Finding newest movies");
        return new ResponseEntity<List<Movie>>(movieService.allMoviesSortedByReleaseDate(), HttpStatus.OK);
    }

    @GetMapping("/director/{director}")
    public ResponseEntity<List<Movie>> findMoviesByDirector(@PathVariable String director) {
        logger.info("Finding movies by director: " + director);
        return new ResponseEntity<List<Movie>>(movieService.findMoviesByDirector(director), HttpStatus.OK);
    }

    @GetMapping("/bestrated")
    public ResponseEntity<List<Movie>> findBestMovies() {
        logger.info("Finding best rated movies");
        return new ResponseEntity<List<Movie>>(movieService.allMoviesSortedByRating(), HttpStatus.OK);
    }
}