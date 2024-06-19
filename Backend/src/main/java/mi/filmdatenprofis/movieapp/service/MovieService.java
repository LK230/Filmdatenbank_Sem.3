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

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Async
    public CompletableFuture<List<Movie>> allMovies() {
        logger.info("Fetching all movies in thread: " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture(movieRepository.findAll());
    }

    public Optional<Movie> singleMovie(String imdbId) {
        logger.info("Fetching movie with ID: " + imdbId);
        return movieRepository.findMovieByImdbId(imdbId);
    }

    public List<Movie> findMoviesByTitle(String title) {
        logger.info("Finding movies with title: " + title);
        return movieRepository.findMovieByTitleContainingIgnoreCase(title);
    }

    public List<Movie> allMoviesSortedByReleaseDate() {
        logger.info("Sorting movies by release date");
        return movieRepository.findAllByOrderByReleaseDateDesc();
    }

    public List<Movie> findMoviesByGenre(String genre) {
        logger.info("Finding movies with genre: " + genre);
        return movieRepository.findByGenresIgnoreCase(genre);
    }

    public List<Movie> findMoviesByDirector(String director) {
        logger.info("Finding movies by director: " + director);
        return movieRepository.findByDirectorIgnoreCase(director);
    }

    public List<Movie> allMoviesSortedByRating() {
        logger.info("Sorting movies by rating");
        return movieRepository.findAllByOrderByRating();
    }

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