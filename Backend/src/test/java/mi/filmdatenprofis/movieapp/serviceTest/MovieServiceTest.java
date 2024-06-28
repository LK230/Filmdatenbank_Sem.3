package mi.filmdatenprofis.movieapp.serviceTest;

import mi.filmdatenprofis.movieapp.model.Movie;
import mi.filmdatenprofis.movieapp.repository.MovieRepository;
import mi.filmdatenprofis.movieapp.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie movie1;
    private Movie movie2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample movies for testing
        movie1 = new Movie();
        movie1.setImdbId("tt0111161");
        movie1.setTitle("The Shawshank Redemption");
        movie1.setReleaseDate("1994-09-23");
        movie1.setDirector("Frank Darabont");
        movie1.setRuntime("142 min");
        movie1.setActors(Arrays.asList("Tim Robbins", "Morgan Freeman", "Bob Gunton"));
        movie1.setPlot("Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.");
        movie1.setTrailerLink("https://www.youtube.com/watch?v=6hB3S9bIaco");
        movie1.setRating(9.3);
        movie1.setReviews(2345678);
        movie1.setRated("R");
        movie1.setGenres(Arrays.asList("Drama"));
        movie1.setPoster("https://example.com/poster.jpg");
        movie1.setBackdrops(Arrays.asList("https://example.com/backdrop1.jpg", "https://example.com/backdrop2.jpg"));
        movie1.setReviewIds(Collections.emptyList()); // Assuming no reviews initially

        movie2 = new Movie();
        movie2.setImdbId("tt0137523");
        movie2.setTitle("Fight Club");
        movie2.setReleaseDate("1999-10-15");
        movie2.setDirector("David Fincher");
        movie2.setRuntime("139 min");
        movie2.setActors(Arrays.asList("Brad Pitt", "Edward Norton", "Helena Bonham Carter"));
        movie2.setPlot("An insomniac office worker and a devil-may-care soapmaker form an underground fight club that evolves into something much, much more.");
        movie2.setTrailerLink("https://www.youtube.com/watch?v=SUXWAEX2jlg");
        movie2.setRating(8.8);
        movie2.setReviews(1829763); // Assuming 2 million reviews
        movie2.setRated("R");
        movie2.setGenres(Arrays.asList("Drama", "Thriller"));
        movie2.setPoster("https://example.com/poster2.jpg");
        movie2.setBackdrops(Arrays.asList("https://example.com/backdrop3.jpg", "https://example.com/backdrop4.jpg"));
        movie2.setReviewIds(Collections.emptyList()); // Assuming no reviews initially

    }

    @Test
    public void testAllMovies() {
        // Mocking repository call to return list of movies
        when(movieRepository.findAll()).thenReturn(Arrays.asList(movie1, movie2));

        List<Movie> result = movieService.allMovies();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("The Shawshank Redemption", result.get(0).getTitle());
        assertEquals("Fight Club", result.get(1).getTitle());
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    public void testSingleMovie() {
        // Mocking repository call to return a movie with specific IMDb ID
        when(movieRepository.findMovieByImdbId("tt0111161")).thenReturn(Optional.of(movie1));

        Optional<Movie> result = movieService.singleMovie("tt0111161");

        assertTrue(result.isPresent());
        assertEquals("The Shawshank Redemption", result.get().getTitle());
        verify(movieRepository, times(1)).findMovieByImdbId("tt0111161");
    }

    @Test
    public void testFindMoviesByTitle() {
        // Mocking repository call to return movies with matching title
        when(movieRepository.findMovieByTitleContainingIgnoreCase("shawshank")).thenReturn(Arrays.asList(movie1));

        List<Movie> result = movieService.findMoviesByTitle("shawshank");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("The Shawshank Redemption", result.get(0).getTitle());
        verify(movieRepository, times(1)).findMovieByTitleContainingIgnoreCase("shawshank");
    }


    @Test
    public void testGetMoviesByGenre() {
        // Mocking repository call to return list of movies
        when(movieRepository.findAll()).thenReturn(Arrays.asList(movie1, movie2));

        Map<String, List<Movie>> result = movieService.getMoviesByGenre();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsKey("Drama"));
        assertTrue(result.containsKey("Thriller"));
        assertEquals(2, result.get("Drama").size());
        assertEquals(1, result.get("Thriller").size());
        verify(movieRepository, times(1)).findAll();
    }
}

