package mi.filmdatenprofis.movieapp.service;

import mi.filmdatenprofis.movieapp.model.Movie;
import mi.filmdatenprofis.movieapp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

// Indicates that this class is a service component in the Spring framework
@Service
public class MovieService {

    // Automatically injects the MovieRepository dependency
    @Autowired
    private MovieRepository movieRepository;

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Async
    public CompletableFuture<List<Movie>> allMovies() {
        logger.info("Fetching all movies in thread: " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture(movieRepository.findAll());
    }

    /**
     * Fetches a single movie by its IMDB ID.
     * @param imdbId the IMDB ID of the movie to fetch.
     * @return an Optional containing the movie if found, or empty if not found.
     */
    public Optional<Movie> singleMovie(String imdbId) {
        logger.info("Fetching movie with ID: " + imdbId);
        return movieRepository.findMovieByImdbId(imdbId);
    }

    /**
     * Finds movies with titles containing the specified string, case insensitive.
     * @param title the string to search for in movie titles.
     * @return a list of movies with titles containing the specified string.
     */
    public List<Movie> findMoviesByTitle(String title) {
        logger.info("Finding movies with title: " + title);
        return movieRepository.findMovieByTitleContainingIgnoreCase(title);
    }

    /**
     * Fetches all movies sorted by their release date in descending order.
     * @return a list of all movies sorted by release date.
     */
    public List<Movie> allMoviesSortedByReleaseDate() {
        logger.info("Sorting movies by release date");
        return movieRepository.findAllByOrderByReleaseDateDesc();
    }

    /**
     * Finds movies by their genre, case insensitive.
     * @param genre the genre to search for.
     * @return a list of movies with the specified genre.
     */
    public List<Movie> findMoviesByGenre(String genre) {
        logger.info("Finding movies with genre: " + genre);
        return movieRepository.findByGenresIgnoreCase(genre);
    }

    /**
     * Finds movies by their director, case insensitive.
     * @param director the director to search for.
     * @return a list of movies directed by the specified director.
     */
    public List<Movie> findMoviesByDirector(String director) {
        logger.info("Finding movies by director: " + director);
        return movieRepository.findByDirectorContainingIgnoreCase(director);
    }

    /**
     * Fetches all movies sorted by their rating in ascending order.
     * @return a list of all movies sorted by rating.
     */
    public List<Movie> allMoviesSortedByRating() {
        logger.info("Sorting movies by rating");
        return movieRepository.findAllByOrderByRating();
    }

    /**
     * Groups movies by their genres.
     * @return a map where the key is the genre and the value is a list of movies in that genre.
     */
    public Map<String, List<Movie>> getMoviesByGenre() {
        logger.info("Getting movies by genre");

        // Fetch all movies
        List<Movie> movies = movieRepository.findAll();

        // Group movies by genre using parallel streams
        Map<String, List<Movie>> moviesByGenre = movies.parallelStream()
                .flatMap(movie -> movie.getGenres().stream()
                        .map(genre -> new AbstractMap.SimpleEntry<>(genre, movie)))
                .collect(Collectors.groupingByConcurrent(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        return moviesByGenre;
    }

}