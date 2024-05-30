package mi.filmdatenprofis.movieapp.service;

import mi.filmdatenprofis.movieapp.model.Movie;
import mi.filmdatenprofis.movieapp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    public List<Movie> allMovies() {
        logger.info("Fetching all movies");
        return movieRepository.findAll();
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
        List<Movie> movies = movieRepository.findAll();
        Map<String, List<Movie>> moviesByGenre = new HashMap<>();

        for (Movie movie : movies) {
            for (String genre : movie.getGenres()) {
                moviesByGenre.computeIfAbsent(genre, k -> new ArrayList<>()).add(movie);
            }
        }
        return moviesByGenre;
    }
}