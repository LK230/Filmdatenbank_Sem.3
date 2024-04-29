package mi.filmdatenprofis.movieapp.service;

// Importing necessary libraries and classes
import mi.filmdatenprofis.movieapp.model.Movie;
import mi.filmdatenprofis.movieapp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}

