package mi.filmdatenprofis.movieapp.serviceTest;

import mi.filmdatenprofis.movieapp.model.*;
import mi.filmdatenprofis.movieapp.repository.*;
import mi.filmdatenprofis.movieapp.service.ReviewService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private ReviewService reviewService;

    private User user;
    private Movie movie;
    private Review review;

    @BeforeEach
    void setUp() {
        // Initialize test data
        user = new User("John", "Doe", "john_doe", "password", "john.doe@example.com");

        movie = new Movie();
        movie.setId(new ObjectId());
        movie.setImdbId("tt0111161");
        movie.setTitle("The Shawshank Redemption");
        movie.setReviews(1);
        movie.setReviewIds(new ArrayList<>());
        movie.setReleaseDate("1994-09-23");
        movie.setDirector("Frank Darabont");
        movie.setRuntime("142 min");
        movie.setActors(Arrays.asList("Tim Robbins", "Morgan Freeman", "Bob Gunton"));
        movie.setPlot("Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.");
        movie.setTrailerLink("https://www.youtube.com/watch?v=6hB3S9bIaco");
        movie.setRating(9.3);
        movie.setRated("R");
        movie.setGenres(Arrays.asList("Drama"));
        movie.setPoster("https://example.com/poster.jpg");
        movie.setBackdrops(Arrays.asList("https://example.com/backdrop1.jpg", "https://example.com/backdrop2.jpg"));

        review = new Review("Great movie!", 5, "tt0111161", LocalDateTime.now(), LocalDateTime.now(), "john_doe");
        review.setId(new ObjectId());
        movie.getReviewIds().add(review);
        user.getProfile().addReview(review);
    }

    @Test
    public void testCalculateAverageRating_NoReviews() {
        // Test calculating average rating with no reviews
        movie.setReviews(0);
        movie.setReviewIds(new ArrayList<>());

        when(movieRepository.findMovieByImdbId("tt0111161")).thenReturn(Optional.of(movie));

        Double result = reviewService.calculateAverageRating("tt0111161");

        // Verify the average rating is 0.0
        assertEquals(0.0, result);
    }

    @Test
    public void testCalculateAverageRating_WithReviews() {
        // Test calculating average rating with existing reviews
        when(movieRepository.findMovieByImdbId("tt0111161")).thenReturn(Optional.of(movie));

        Double result = reviewService.calculateAverageRating("tt0111161");

        // Verify the average rating is 5.0
        assertEquals(5.0, result);
    }

    @Test
    public void testCreateReview_UserNotFound() {
        // Test creating a review when user is not found
        when(userRepository.findByEmailIgnoreCase("john.doe@example.com")).thenReturn(Optional.empty());

        Review result = reviewService.createReview("Great movie!", 5, "tt0111161", "john.doe@example.com");

        // Verify no review is created
        assertNull(result);
        verify(reviewRepository, never()).insert(any(Review.class));
    }

    @Test
    public void testDeleteReview_ReviewNotFound() {
        // Test deleting a review when review is not found
        when(reviewRepository.findById(any(ObjectId.class))).thenReturn(Optional.empty());

        boolean result = reviewService.deleteReview(new ObjectId().toHexString());

        // Verify no review is deleted
        assertFalse(result);
        verify(reviewRepository, never()).delete(any(Review.class));
    }


    @Test
    public void testUpdateReview_ReviewNotFound() {
        // Test updating a review when review is not found
        when(reviewRepository.findById(any(ObjectId.class))).thenReturn(Optional.empty());

        boolean result = reviewService.updateReview(new ObjectId().toHexString(), "Updated review", 4);

        // Verify no review is updated
        assertFalse(result);
        verify(reviewRepository, never()).save(any(Review.class));
    }
}

