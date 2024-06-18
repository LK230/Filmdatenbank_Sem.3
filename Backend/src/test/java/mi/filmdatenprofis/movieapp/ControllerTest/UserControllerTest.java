package mi.filmdatenprofis.movieapp.ControllerTest;

import mi.filmdatenprofis.movieapp.controller.UserController;
import mi.filmdatenprofis.movieapp.model.User;
import mi.filmdatenprofis.movieapp.model.UserProfile;
import mi.filmdatenprofis.movieapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        // Sample User object for testing
        sampleUser = new User();
        sampleUser.setUsername("john.doe");
        sampleUser.setEmail("john.doe@example.com");
        sampleUser.setPassword("password");
        sampleUser.setProfile(new UserProfile("john.doe"));
    }

    @Test
    public void testCreateUser_Success() {
        // Mocking userService methods
        when(userService.isEmailAlreadyTaken("john.doe@example.com")).thenReturn(false);
        when(userService.isUsernameAlreadyTaken("john.doe")).thenReturn(false);
        when(userService.isValidEmail("john.doe@example.com")).thenReturn(true);

        // Calling the controller method
        ResponseEntity<String> response = userController.createUser(sampleUser);

        // Verifying the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User was created successfully!", response.getBody());

        // Verifying that userService.createUser(...) was called once
        verify(userService, times(1)).createUser(sampleUser);
    }

    @Test
    public void testCreateUser_EmailTaken() {
        // Mocking userService methods
        when(userService.isEmailAlreadyTaken("john.doe@example.com")).thenReturn(true);

        // Calling the controller method
        ResponseEntity<String> response = userController.createUser(sampleUser);

        // Verifying the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The entered e-mail address or username is already taken", response.getBody());

        // Verifying that userService.createUser(...) was not called
        verify(userService, never()).createUser(sampleUser);
    }

    @Test
    public void testCreateUser_InvalidEmail() {
        // Mocking userService methods
        when(userService.isEmailAlreadyTaken("john.doe@example.com")).thenReturn(false);
        when(userService.isValidEmail("john.doe@example.com")).thenReturn(false);

        // Calling the controller method
        ResponseEntity<String> response = userController.createUser(sampleUser);

        // Verifying the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The entered e-mail address is not valid", response.getBody());

        // Verifying that userService.createUser(...) was not called
        verify(userService, never()).createUser(sampleUser);
    }

    @Test
    public void testLoginUser_Success() {
        // Mocking userService method
        when(userService.authenticateUser("john.doe@example.com", "password")).thenReturn(true);

        // Calling the controller method
        ResponseEntity<String> response = userController.loginUser("john.doe@example.com", "password");

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successfully!", response.getBody());
    }

    @Test
    public void testLoginUser_Unauthorized() {
        // Mocking userService method
        when(userService.authenticateUser("john.doe@example.com", "wrongpassword")).thenReturn(false);

        // Calling the controller method
        ResponseEntity<String> response = userController.loginUser("john.doe@example.com", "wrongpassword");

        // Verifying the response
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Wrong email or password", response.getBody());
    }

    @Test
    public void testGetUserProfile_Success() {
        // Mocking userService methods
        when(userService.authenticateUser("john.doe@example.com", "password")).thenReturn(true);
        when(userService.userProfile("john.doe")).thenReturn(Optional.of(sampleUser.getProfile()));

        // Calling the controller method
        ResponseEntity<?> response = userController.getUserProfile("john.doe", "john.doe@example.com", "password");

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(sampleUser.getProfile()), response.getBody());
    }

    @Test
    public void testGetUserProfile_NotFound() {
        // Mocking userService method
        when(userService.authenticateUser("john.doe@example.com", "password")).thenReturn(false);

        // Calling the controller method
        ResponseEntity<?> response = userController.getUserProfile("john.doe", "john.doe@example.com", "password");

        // Verifying the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Failed to load profile", response.getBody());
    }

    @Test
    public void testAddToFavorites_Success() {
        // Mocking userService method
        when(userService.addFavorites("john.doe", "tt1234567")).thenReturn(true);

        // Calling the controller method
        ResponseEntity<?> response = userController.addToFavorites("john.doe", "tt1234567");

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Movie added to favorites successfully", response.getBody());
    }

    @Test
    public void testAddToFavorites_Failure() {
        // Mocking userService method
        when(userService.addFavorites("john.doe", "tt1234567")).thenReturn(false);

        // Calling the controller method
        ResponseEntity<?> response = userController.addToFavorites("john.doe", "tt1234567");

        // Verifying the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Wrong username, movie or movie is already in favorites", response.getBody());
    }

    @Test
    public void testRemoveFromFavorites_Success() {
        // Mocking userService method
        when(userService.removeFavorites("john.doe", "tt1234567")).thenReturn(true);

        // Calling the controller method
        ResponseEntity<?> response = userController.removeFromFavorites("john.doe", "tt1234567");

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Movie removed from favorites successfully", response.getBody());
    }

    @Test
    public void testRemoveFromFavorites_Failure() {
        // Mocking userService method
        when(userService.removeFavorites("john.doe", "tt1234567")).thenReturn(false);

        // Calling the controller method
        ResponseEntity<?> response = userController.removeFromFavorites("john.doe", "tt1234567");

        // Verifying the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Wrong username, movie or movie is not in favorites", response.getBody());
    }

    @Test
    public void testDeleteUser_Success() {
        // Mocking userService methods
        when(userService.authenticateUser("john.doe@example.com", "password")).thenReturn(true);
        when(userService.deleteUser("john.doe@example.com")).thenReturn(true);

        // Calling the controller method
        ResponseEntity<String> response = userController.deleteUser("john.doe@example.com", "password");

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User was deleted", response.getBody());
    }

    @Test
    public void testDeleteUser_Unauthorized() {
        // Mocking userService method
        when(userService.authenticateUser("john.doe@example.com", "wrongpassword")).thenReturn(false);

        // Calling the controller method
        ResponseEntity<String> response = userController.deleteUser("john.doe@example.com", "wrongpassword");

        // Verifying the response
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("An error occurred", response.getBody());
    }

    @Test
    public void testUpdatePassword_Success() {
        // Mocking userService methods
        when(userService.authenticateUser("john.doe@example.com", "password")).thenReturn(true);
        when(userService.changePassword("john.doe@example.com", "newpassword")).thenReturn(true);

        // Calling the controller method
        ResponseEntity<String> response = userController.updatePassword("john.doe@example.com", "password", "newpassword");

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password changed successfully!", response.getBody());
    }

    @Test
    public void testUpdatePassword_Unauthorized() {
        // Mocking userService method
        when(userService.authenticateUser("john.doe@example.com", "wrongpassword")).thenReturn(false);

        // Calling the controller method
        ResponseEntity<String> response = userController.updatePassword("john.doe@example.com", "wrongpassword", "newpassword");

        // Verifying the response
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("An error occurred", response.getBody());
    }

    @Test
    public void testUpdateEmail_Success() {
        // Mocking userService methods
        when(userService.authenticateUser("john.doe@example.com", "password")).thenReturn(true);
        when(userService.changeEmail("john.doe@example.com", "newemail@example.com")).thenReturn(true);

        // Calling the controller method
        ResponseEntity<String> response = userController.updateEmail("john.doe@example.com", "password", "newemail@example.com");

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Email changed successfully!", response.getBody());
    }

    @Test
    public void testUpdateEmail_Unauthorized() {
        // Mocking userService method
        when(userService.authenticateUser("john.doe@example.com", "wrongpassword")).thenReturn(false);

        // Calling the controller method
        ResponseEntity<String> response = userController.updateEmail("john.doe@example.com", "wrongpassword", "newemail@example.com");

        // Verifying the response
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("An error occurred", response.getBody());
    }
}
