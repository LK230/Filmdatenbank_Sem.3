package mi.filmdatenprofis.movieapp.service;

// Importing necessary libraries and classes
import mi.filmdatenprofis.movieapp.model.Movie;
import mi.filmdatenprofis.movieapp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

// Annotation to indicate this is a Service
@Service
public class MovieService {
    // Autowiring the MovieRepository to use its methods
    @Autowired
    private MovieRepository movieRepository;

    // Method to get all movies from the repository
    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }

    // Method to get a single movie by its IMDB ID from the repository
    public Optional<Movie> singleMovie(String imdbId) {
        return movieRepository.findMovieByImdbId(imdbId);
    }

    // Method to get a movie by its title
    public List<Movie> findMoviesByTitle(String title) {
        return movieRepository.findMovieByTitleContainingIgnoreCase(title); }

    //method to sort movies by latest release date
    public List<Movie> allMoviesSortedByReleaseDate() {
        return movieRepository.findAllByOrderByReleaseDateDesc();
    }

    //method to find movies by its genre
    public List<Movie> findMoviesByGenre(String genre) {
        return movieRepository.findByGenresIgnoreCase(genre);
    }

    //method to find movies by a specific director
    public List<Movie> findMoviesByDirector(String director) { return movieRepository.findByDirectorIgnoreCase(director); }

    //method to find movies sorted by rating
    public List<Movie> allMoviesSortedByRating() { return movieRepository.findAllByOrderByRating(); }

    //method to get all movies sorted by genre
    public Map<String, List<Movie>> getMoviesByGenre() {

        // Get all movies from repo
        List<Movie> movies = movieRepository.findAll();
        // Create a new HashMap to store the movies categorized by genre
        Map<String, List<Movie>> moviesByGenre = new HashMap<>();

        // Iterate over each movie in the list of movies
        for (Movie movie : movies) {
            // Iterate over each genre associated with the current movie
            for (String genre : movie.getGenres()) {
                // For each genre, check if the genre is already a key in the map
                // If the genre is not a key, add it with an empty list as its value
                // Then, add the current movie to the list of movies for this genre
                moviesByGenre
                        .computeIfAbsent(genre, k -> new ArrayList<>())
                        .add(movie);
            }
        }
        // Return the map containing the genres as keys and the list of movies as values
        return moviesByGenre;
    }
}

