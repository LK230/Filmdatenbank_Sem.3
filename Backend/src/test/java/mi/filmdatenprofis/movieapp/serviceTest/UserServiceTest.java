package mi.filmdatenprofis.movieapp.serviceTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import mi.filmdatenprofis.movieapp.model.Movie;
import mi.filmdatenprofis.movieapp.model.User;
import mi.filmdatenprofis.movieapp.model.UserProfile;
import mi.filmdatenprofis.movieapp.repository.MovieRepository;
import mi.filmdatenprofis.movieapp.repository.UserProfileRepository;
import mi.filmdatenprofis.movieapp.repository.UserRepository;
import mi.filmdatenprofis.movieapp.service.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class) // Use Mockito's extension to enable Mockito annotations
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserProfile userProfile;
    private Movie movie;

    @BeforeEach
    void setUp() {
        // Initialize User and UserProfile objects
        user = new User("John", "Doe", "john_doe", "password", "john.doe@example.com");
        userProfile = new UserProfile("john_doe");
        user.setProfile(userProfile);

        // Initialize a Movie object with predefined values
        movie = new Movie();
        movie.setId(new ObjectId());
        movie.setImdbId("tt0111161");
        movie.setTitle("The Shawshank Redemption");
        movie.setReleaseDate("1994-09-23");
        movie.setDirector("Frank Darabont");
        movie.setRuntime("142 min");
        movie.setActors(Arrays.asList("Tim Robbins", "Morgan Freeman", "Bob Gunton"));
        movie.setPlot("Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.");
        movie.setTrailerLink("https://www.youtube.com/watch?v=6hB3S9bIaco");
        movie.setRating(9.3);
        movie.setReviews(2345678);
        movie.setRated("R");
        movie.setGenres(Arrays.asList("Drama"));
        movie.setPoster("https://example.com/poster.jpg");
        movie.setBackdrops(Arrays.asList("https://example.com/backdrop1.jpg", "https://example.com/backdrop2.jpg"));
        movie.setReviewIds(Collections.emptyList());
    }

    @Test
    public void testIsEmailAlreadyTaken_EmailExists() {
        // Mocking repository call to return a user when the email exists
        when(userRepository.findByEmailIgnoreCase("john.doe@example.com")).thenReturn(Optional.of(user));

        boolean result = userService.isEmailAlreadyTaken("john.doe@example.com");

        assertTrue(result); // The result should be true as the email exists
        verify(userRepository, times(1)).findByEmailIgnoreCase("john.doe@example.com"); // Verify the repository was called once
    }

    @Test
    public void testIsEmailAlreadyTaken_EmailDoesNotExist() {
        // Mocking repository call to return an empty Optional when the email does not exist
        when(userRepository.findByEmailIgnoreCase("john.doe@example.com")).thenReturn(Optional.empty());

        boolean result = userService.isEmailAlreadyTaken("john.doe@example.com");

        assertFalse(result); // The result should be false as the email does not exist
        verify(userRepository, times(1)).findByEmailIgnoreCase("john.doe@example.com"); // Verify the repository was called once
    }

    @Test
    public void testIsUsernameAlreadyTaken_UsernameExists() {
        // Mocking repository call to return a user when the username exists
        when(userRepository.findByUsernameIgnoreCase("john_doe")).thenReturn(Optional.of(user));

        boolean result = userService.isUsernameAlreadyTaken("john_doe");

        assertTrue(result); // The result should be true as the username exists
        verify(userRepository, times(1)).findByUsernameIgnoreCase("john_doe"); // Verify the repository was called once
    }

    @Test
    public void testIsUsernameAlreadyTaken_UsernameDoesNotExist() {
        // Mocking repository call to return an empty Optional when the username does not exist
        when(userRepository.findByUsernameIgnoreCase("john_doe")).thenReturn(Optional.empty());

        boolean result = userService.isUsernameAlreadyTaken("john_doe");

        assertFalse(result); // The result should be false as the username does not exist
        verify(userRepository, times(1)).findByUsernameIgnoreCase("john_doe"); // Verify the repository was called once
    }

    @Test
    public void testIsValidEmail_ValidEmail() {
        boolean result = userService.isValidEmail("john.doe@example.com");

        assertTrue(result); // The result should be true as the email is valid
    }

    @Test
    public void testIsValidEmail_InvalidEmail() {
        boolean result = userService.isValidEmail("john.doe@example");

        assertFalse(result); // The result should be false as the email is invalid
    }

    @Test
    public void testCreateUser() {
        // Mocking repository calls for saving a user and user profile
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser); // The created user should not be null
        assertEquals("john.doe@example.com", createdUser.getEmail()); // The email should match
        verify(userRepository, times(1)).save(any(User.class)); // Verify the user repository was called once
        verify(userProfileRepository, times(1)).save(any(UserProfile.class)); // Verify the user profile repository was called once
    }

    @Test
    public void testAuthenticateUser_ValidCredentials() {
        // Mocking repository call to return a user with the correct email
        when(userRepository.findByEmailIgnoreCase("john.doe@example.com")).thenReturn(Optional.of(user));

        boolean result = userService.authenticateUser("john.doe@example.com", "password");

        assertTrue(result); // The result should be true as the credentials are valid
        verify(userRepository, times(1)).findByEmailIgnoreCase("john.doe@example.com"); // Verify the repository was called once
    }

    @Test
    public void testAuthenticateUser_InvalidCredentials() {
        // Mocking repository call to return a user with the correct email
        when(userRepository.findByEmailIgnoreCase("john.doe@example.com")).thenReturn(Optional.of(user));

        boolean result = userService.authenticateUser("john.doe@example.com", "wrongpassword");

        assertFalse(result); // The result should be false as the credentials are invalid
        verify(userRepository, times(1)).findByEmailIgnoreCase("john.doe@example.com"); // Verify the repository was called once
    }

    @Test
    public void testAddFavorites_Success() {
        // Mocking repository calls to return a user and a movie
        when(userRepository.findByUsernameIgnoreCase("john_doe")).thenReturn(Optional.of(user));
        when(movieRepository.findMovieByImdbId("tt0111161")).thenReturn(Optional.of(movie));

        boolean result = userService.addFavorites("john_doe", "tt0111161");

        assertTrue(result); // The result should be true as the movie was successfully added
        assertTrue(user.getProfile().getFavorites().contains(movie)); // The movie should be in the user's favorites
        verify(userRepository, times(1)).save(user); // Verify the user repository was called once
        verify(userProfileRepository, times(1)).save(userProfile); // Verify the user profile repository was called once
    }

    @Test
    public void testRemoveFavorites_Success() {
        // Adding a movie to the user's favorites and mocking repository calls to return a user and a movie
        user.getProfile().getFavorites().add(movie);
        when(userRepository.findByUsernameIgnoreCase("john_doe")).thenReturn(Optional.of(user));
        when(movieRepository.findMovieByImdbId("tt0111161")).thenReturn(Optional.of(movie));

        boolean result = userService.removeFavorites("john_doe", "tt0111161");

        assertTrue(result); // The result should be true as the movie was successfully removed
        assertFalse(user.getProfile().getFavorites().contains(movie)); // The movie should not be in the user's favorites anymore
        verify(userRepository, times(1)).save(user); // Verify the user repository was called once
        verify(userProfileRepository, times(1)).save(userProfile); // Verify the user profile repository was called once
    }

    @Test
    public void testDeleteUser_Success() {
        // Mocking repository calls to return a user and a user profile
        when(userRepository.findByEmailIgnoreCase("john.doe@example.com")).thenReturn(Optional.of(user));
        when(userProfileRepository.findByUsernameIgnoreCase("john_doe")).thenReturn(Optional.of(userProfile));

        boolean result = userService.deleteUser("john.doe@example.com");

        assertTrue(result); // The result should be true as the user was successfully deleted
        verify(userRepository, times(1)).delete(user); // Verify the user repository was called once
        verify(userProfileRepository, times(1)).delete(userProfile); // Verify the user profile repository was called once
    }

    @Test
    public void testChangePassword_Success() {
        // Mocking repository call to return a user with the correct email
        when(userRepository.findByEmailIgnoreCase("john.doe@example.com")).thenReturn(Optional.of(user));

        boolean result = userService.changePassword("john.doe@example.com", "newpassword");

        assertTrue(result); // The result should be true as the password was successfully changed
        assertEquals("newpassword", user.getPassword()); // The user's password should match the new password
        verify(userRepository, times(1)).save(user); // Verify the user repository was called once
    }

    @Test
    public void testChangeEmail_Success() {
        // Mocking repository calls to return a user with the correct email and no user with the new email
        when(userRepository.findByEmailIgnoreCase("john.doe@example.com")).thenReturn(Optional.of(user));
        when(userRepository.findByEmailIgnoreCase("new.email@example.com")).thenReturn(Optional.empty());

        boolean result = userService.changeEmail("john.doe@example.com", "new.email@example.com");

        assertTrue(result); // The result should be true as the email was successfully changed
        assertEquals("new.email@example.com", user.getEmail()); // The user's email should match the new email
        verify(userRepository, times(1)).save(user); // Verify the user repository was called once
    }
}

