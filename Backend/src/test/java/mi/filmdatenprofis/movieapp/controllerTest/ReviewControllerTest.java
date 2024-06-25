package mi.filmdatenprofis.movieapp.controllerTest;

import mi.filmdatenprofis.movieapp.controller.ReviewController;
import mi.filmdatenprofis.movieapp.model.Review;
import mi.filmdatenprofis.movieapp.service.ReviewService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private Review sampleReview;

    @BeforeEach
    void setUp() {
        // Sample Review object for testing
        sampleReview = new Review();
        sampleReview.setId(new ObjectId());
        sampleReview.setBody("Sample review body");
        sampleReview.setRating(4);
        sampleReview.setImdbId("tt1234567");
        sampleReview.setCreatedBy("sampleUser");
    }

    @Test
    public void testCreateReview_Success() {
        // Mocking the service call
        when(reviewService.createReview("Sample review body", 4, "tt1234567", "sampleUser")).thenReturn(sampleReview);

        // Calling the controller method
        ResponseEntity<?> response = reviewController.createReview("Sample review body", 4, "tt1234567", "sampleUser");

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleReview, response.getBody());

        // Verifying that reviewService.createReview(...) was called exactly once with the correct parameters
        verify(reviewService, times(1)).createReview("Sample review body", 4, "tt1234567", "sampleUser");
    }

    @Test
    public void testCreateReview_InvalidRating() {
        // Calling the controller method with an invalid rating
        ResponseEntity<?> response = reviewController.createReview("Sample review body", 6, "tt1234567", "sampleUser");

        // Verifying the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("An error occurred creating the review (Rating too high or too low)", response.getBody());

        // Verifying that reviewService.createReview(...) was not called
        verify(reviewService, never()).createReview(anyString(), anyInt(), anyString(), anyString());
    }

    @Test
    public void testCreateReview_ServiceReturnsNull() {
        // Mocking the service call to return null
        when(reviewService.createReview("Sample review body", 4, "tt1234567", "sampleUser")).thenReturn(null);

        // Calling the controller method
        ResponseEntity<?> response = reviewController.createReview("Sample review body", 4, "tt1234567", "sampleUser");

        // Verifying the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("An error occurred creating the review", response.getBody());

        // Verifying that reviewService.createReview(...) was called exactly once with the correct parameters
        verify(reviewService, times(1)).createReview("Sample review body", 4, "tt1234567", "sampleUser");
    }

    @Test
    public void testDeleteReview_Success() {
        // Mocking the service call
        when(reviewService.deleteReview("1")).thenReturn(true);

        // Calling the controller method
        ResponseEntity<String> response = reviewController.deleteReview("1");

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Review was deleted", response.getBody());

        // Verifying that reviewService.deleteReview(...) was called exactly once with the correct parameter
        verify(reviewService, times(1)).deleteReview("1");
    }

    @Test
    public void testDeleteReview_Failure() {
        // Mocking the service call to return false
        when(reviewService.deleteReview("1")).thenReturn(false);

        // Calling the controller method
        ResponseEntity<String> response = reviewController.deleteReview("1");

        // Verifying the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred removing the review", response.getBody());

        // Verifying that reviewService.deleteReview(...) was called exactly once with the correct parameter
        verify(reviewService, times(1)).deleteReview("1");
    }

    @Test
    public void testUpdateReview_Success() {
        // Mocking the service call
        when(reviewService.updateReview("1", "Updated review body", 5)).thenReturn(true);

        // Calling the controller method
        ResponseEntity<String> response = reviewController.updateReview("1", "Updated review body", 5);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Review was updated successfully", response.getBody());

        // Verifying that reviewService.updateReview(...) was called exactly once with the correct parameters
        verify(reviewService, times(1)).updateReview("1", "Updated review body", 5);
    }

    @Test
    public void testUpdateReview_NotFound() {
        // Mocking the service call to return false
        when(reviewService.updateReview("1", "Updated review body", 5)).thenReturn(false);

        // Calling the controller method
        ResponseEntity<String> response = reviewController.updateReview("1", "Updated review body", 5);

        // Verifying the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("An error occurred updating the review", response.getBody());

        // Verifying that reviewService.updateReview(...) was called exactly once with the correct parameters
        verify(reviewService, times(1)).updateReview("1", "Updated review body", 5);
    }

}

