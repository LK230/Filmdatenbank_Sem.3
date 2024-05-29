package mi.filmdatenprofis.movieapp.controller;

// Importing necessary libraries and classes
import mi.filmdatenprofis.movieapp.model.Movie;
import mi.filmdatenprofis.movieapp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

// Annotation to allow cross-origin requests
@CrossOrigin(origins = "*")

// Annotation to indicate this is a REST controller
@RestController

// Mapping this controller to respond to "/api/v1/movies" endpoint
@RequestMapping("/movies")
public class MovieController {
    // Autowiring the MovieService to use its methods
    @Autowired
    private MovieService movieService;

    // Method to handle GET requests at the base endpoint and return all movies
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return new ResponseEntity<List<Movie>>(movieService.allMovies(), HttpStatus.OK);
    }

    // Method to handle GET requests at the endpoint "/{imdbId}" and return a specific movie
    @GetMapping("/{imdbId}")
    public ResponseEntity<Optional<Movie>> getSingleMovie(@PathVariable String imdbId) {
        return new ResponseEntity<Optional<Movie>>(movieService.singleMovie(imdbId), HttpStatus.OK);
    }

    // Method to handle GET requests at the endpoint "/{title}" and return a specific movie by its title
    @GetMapping("/title/{title}")
    public ResponseEntity<List<Movie>> findMoviesByTitle(@PathVariable String title) {
        return new ResponseEntity<List<Movie>> (movieService.findMoviesByTitle(title), HttpStatus.OK);
    }

    // Method to handle GET requests at the endpoint "/genre" and return a map which includes sorted movies by genre
    @GetMapping("/genre")
    public Map<String, List<Movie>> getMoviesByGenre() {
        return movieService.getMoviesByGenre();
    }

    // Method to handle GET requests at the endpoint "/{genre}" and return all movies by a specific genre
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Movie>> findMoviesByGenre(@PathVariable String genre) {
        return new ResponseEntity<List<Movie>>(movieService.findMoviesByGenre(genre), HttpStatus.OK);
    }

    // Method to handle GET requests at the endpoint "/newest" and return the latest movies
    @GetMapping("/newest")
    public ResponseEntity<List<Movie>> findNewestMovies() {
        return new ResponseEntity<List<Movie>>(movieService.allMoviesSortedByReleaseDate(), HttpStatus.OK);
    }

    // Method to handle GET requests at the endpoint "/{director}" and return all movies from a specific director
    @GetMapping("/director/{director}")
    public ResponseEntity<List<Movie>> findMoviesByDirector(@PathVariable String director) {
        return new ResponseEntity<List<Movie>>(movieService.findMoviesByDirector(director), HttpStatus.OK);
    }

    // Method to handle GET requests at the endpoint "/bestrated" and return the best rated movies
    @GetMapping("/bestrated")
    public ResponseEntity<List<Movie>> findBestMovies() {
        return new ResponseEntity<List<Movie>>(movieService.allMoviesSortedByRating(), HttpStatus.OK);
    }
}
