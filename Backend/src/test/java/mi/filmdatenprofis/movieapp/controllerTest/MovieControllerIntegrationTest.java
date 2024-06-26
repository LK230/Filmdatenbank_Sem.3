package mi.filmdatenprofis.movieapp.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

import mi.filmdatenprofis.movieapp.model.Movie;
import mi.filmdatenprofis.movieapp.repository.MovieRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    private Movie movie1;
    private Movie movie2;

    @BeforeEach
    void setUp() {
        // Initialize test movies
        movie1 = new Movie(new ObjectId(), "tt0111161", "The Shawshank Redemption", "1994-09-23", "Frank Darabont", "142",
                Arrays.asList("Tim Robbins", "Morgan Freeman"), "Two imprisoned men bond over a number of years...",
                "https://www.youtube.com/watch?v=6hB3S9bIaco", 9.3, 2000000, "R", Arrays.asList("Drama"),
                "https://imageurl.com/shawshank.jpg", Arrays.asList("https://backdropurl.com/shawshank1.jpg"), null);

        movie2 = new Movie(new ObjectId(), "tt0068646", "The Godfather", "1972-03-24", "Francis Ford Coppola", "175",
                Arrays.asList("Marlon Brando", "Al Pacino"), "The aging patriarch of an organized crime dynasty transfers control...",
                "https://www.youtube.com/watch?v=sY1S34973zA", 9.2, 1500000, "R", Arrays.asList("Crime", "Drama"),
                "https://imageurl.com/godfather.jpg", Arrays.asList("https://backdropurl.com/godfather1.jpg"), null);

        // Save test movies in the repository
        movieRepository.save(movie1);
        movieRepository.save(movie2);
    }

    @AfterEach
    void tearDown() {
        // Delete test movies from the repository
        movieRepository.delete(movie1);
        movieRepository.delete(movie2);
    }

    @Test
    @Rollback
    void shouldGetAllMovies() throws Exception {
        // Perform a GET request to the /movies endpoint and expect a 200 OK status
        mockMvc.perform(get("/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[?(@.title == 'The Shawshank Redemption')]").exists())
                .andExpect(jsonPath("$[?(@.title == 'The Godfather')]").exists());
    }
/*
    @Test
    @Rollback
    void shouldGetSingleMovie() throws Exception {
        // Perform a GET request to the /movies/{imdbId} endpoint and expect the correct movie data
        mockMvc.perform(get("/movies/{imdbId}", movie1.getImdbId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("The Shawshank Redemption")))
                .andExpect(jsonPath("$.director", is("Frank Darabont")));
    }

    @Test
    @Rollback
    void shouldFindMoviesByTitle() throws Exception {
        // Perform a GET request to the /movies/title/{title} endpoint and expect the correct movie data
        mockMvc.perform(get("/movies/title/{title}", "Godfather")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[?(@.title == 'The Godfather')]").exists());
    }

    @Test
    @Rollback
    void shouldFindMoviesByGenre() throws Exception {
        // Perform a GET request to the /movies/genre/{genre} endpoint and expect the correct movie data
        mockMvc.perform(get("/movies/genre/{genre}", "Drama")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[?(@.title == 'The Shawshank Redemption')]").exists())
                .andExpect(jsonPath("$[?(@.title == 'The Godfather')]").exists());
    }

    @Test
    @Rollback
    void shouldFindMoviesByDirector() throws Exception {
        // Perform a GET request to the /movies/director/{director} endpoint and expect the correct movie data
        mockMvc.perform(get("/movies/director/{director}", "Francis Ford Coppola")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[?(@.title == 'The Godfather')]").exists());
    }

     */

}

