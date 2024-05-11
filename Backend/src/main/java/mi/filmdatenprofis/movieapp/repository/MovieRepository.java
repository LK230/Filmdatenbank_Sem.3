package mi.filmdatenprofis.movieapp.repository;

// Importing necessary libraries and classes
import mi.filmdatenprofis.movieapp.model.Movie;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

// Annotation to indicate this is a Repository
@Repository

public interface MovieRepository extends MongoRepository<Movie, ObjectId> {
    // Method to find a movie by its IMDB ID from the database
    Optional<Movie> findMovieByImdbId(String imdbId);
    List<Movie> findMovieByTitleContainingIgnoreCase(String title);
    List<Movie> findAllByOrderByReleaseDateDesc();
    List<Movie> findByGenresIgnoreCase(String genre);
    List<Movie> findByDirectorIgnoreCase(String director);
    List<Movie> findAllByOrderByRating();
}