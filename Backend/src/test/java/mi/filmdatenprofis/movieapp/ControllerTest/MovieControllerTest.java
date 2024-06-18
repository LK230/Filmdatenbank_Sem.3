package mi.filmdatenprofis.movieapp.ControllerTest;

import mi.filmdatenprofis.movieapp.controller.MovieController;
import mi.filmdatenprofis.movieapp.model.Movie;
import mi.filmdatenprofis.movieapp.service.MovieService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private Movie sampleMovie;

    @BeforeEach
    void setUp() {
        // Sample Movie object for testing
        sampleMovie = new Movie();
        sampleMovie.setId(new ObjectId());
        sampleMovie.setImdbId("tt1234567");
        sampleMovie.setTitle("Sample Movie");
        sampleMovie.setGenres(Arrays.asList("Action", "Adventure"));
        sampleMovie.setDirector("Sample Director");
    }

    @Test
    public void testGetAllMovies() {
        // Mocking the service call
        when(movieService.allMovies()).thenReturn(Arrays.asList(sampleMovie));

        // Calling the controller method
        ResponseEntity<List<Movie>> response = movieController.getAllMovies();

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size()); // Assuming one movie is returned
        assertEquals("Sample Movie", response.getBody().get(0).getTitle());

        // Verifying that movieService.allMovies() was called exactly once
        verify(movieService, times(1)).allMovies();
    }

    @Test
    public void testGetSingleMovie() {
        // Mocking the service call
        when(movieService.singleMovie("tt1234567")).thenReturn(Optional.of(sampleMovie));

        // Calling the controller method
        ResponseEntity<Optional<Movie>> response = movieController.getSingleMovie("tt1234567");

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Sample Movie", response.getBody().get().getTitle());

        // Verifying that movieService.singleMovie("tt1234567") was called exactly once
        verify(movieService, times(1)).singleMovie("tt1234567");
    }

    @Test
    public void testFindMoviesByTitle() {
        // Mocking the service call
        when(movieService.findMoviesByTitle("Sample Movie")).thenReturn(Arrays.asList(sampleMovie));

        // Calling the controller method
        ResponseEntity<List<Movie>> response = movieController.findMoviesByTitle("Sample Movie");

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Sample Movie", response.getBody().get(0).getTitle());

        // Verifying that movieService.findMoviesByTitle("Sample Movie") was called exactly once
        verify(movieService, times(1)).findMoviesByTitle("Sample Movie");
    }

    @Test
    public void testGetMoviesByGenre() {
        // Mocking the service call
        when(movieService.getMoviesByGenre()).thenReturn(Map.of("Action", Arrays.asList(sampleMovie)));

        // Calling the controller method
        Map<String, List<Movie>> response = movieController.getMoviesByGenre();

        // Verifying the response
        assertEquals(1, response.size());
        assertEquals("Sample Movie", response.get("Action").get(0).getTitle());

        // Verifying that movieService.getMoviesByGenre() was called exactly once
        verify(movieService, times(1)).getMoviesByGenre();
    }


}

