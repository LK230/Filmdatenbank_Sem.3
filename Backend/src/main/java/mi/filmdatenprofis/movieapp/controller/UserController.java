package mi.filmdatenprofis.movieapp.controller;

import mi.filmdatenprofis.movieapp.model.User;
import mi.filmdatenprofis.movieapp.model.UserProfile;
import mi.filmdatenprofis.movieapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

// Allows cross-origin requests from any domain
@CrossOrigin(origins = "*")
// Indicates that this class is a REST controller
@RestController
// Maps HTTP requests to /users URL
@RequestMapping("/users")
public class UserController {

    // Injects the UserService dependency
    @Autowired
    private UserService userService;

    // Logger for logging messages
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // Endpoint for creating a new user
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        logger.info("Creating user with email: " + user.getEmail());

        // Checks if the email or username is already taken
        if (userService.isEmailAlreadyTaken(user.getEmail()) || userService.isUsernameAlreadyTaken(user.getUsername())) {
            return new ResponseEntity<>("The entered e-mail address or username is already taken", HttpStatus.BAD_REQUEST);
        }

        // Validates the email format
        if(!userService.isValidEmail(user.getEmail())) {
            return new ResponseEntity<>("The entered e-mail address is not valid", HttpStatus.BAD_REQUEST);
        }

        // Creates the user and returns a success message
        userService.createUser(user);
        return new ResponseEntity<>("User was created successfully!", HttpStatus.CREATED);
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
        logger.info("User login attempt with email: " + email);
        // Authenticates the user
        boolean isAuthenticated = userService.authenticateUser(email, password);

        // Returns appropriate response based on authentication result
        if (isAuthenticated) {
            return new ResponseEntity<>("Login successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Wrong email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    // Endpoint for getting a user's profile
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserProfile(@RequestParam String username,
                                            @RequestParam String email,
                                            @RequestParam String password) {
        logger.info("Getting user profile for username: " + username);

        // Authenticates the user
        if(userService.authenticateUser(email, password)) {
            return new ResponseEntity<Optional<UserProfile>>(userService.userProfile(username), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("Failed to load profile", HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint for adding a movie to a user's favorites
    @PostMapping("/favorites/add")
    public ResponseEntity<?> addToFavorites(@RequestParam String username, @RequestParam String imdbId) {
        logger.info("Adding movie with ID: " + imdbId + " to favorites for user: " + username);

        // Adds the movie to favorites and returns appropriate response
        if (userService.addFavorites(username, imdbId)) {
            return new ResponseEntity<>("Movie added to favorites successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error: Wrong username, movie or movie is already in favorites", HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint for removing a movie from a user's favorites
    @DeleteMapping("/favorites/remove")
    public ResponseEntity<?> removeFromFavorites(@RequestParam String username, @RequestParam String imdbId) {
        logger.info("Removing movie with ID: " + imdbId + " from favorites for user: " + username);

        // Removes the movie from favorites and returns appropriate response
        if (userService.removeFavorites(username, imdbId)) {
            return new ResponseEntity<>("Movie removed from favorites successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error: Wrong username, movie or movie is not in favorites", HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint for deleting a user
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String email, @RequestParam String password) {
        logger.info("Deleting user with email: " + email);

        // Authenticates the user
        boolean isAuthenticated = userService.authenticateUser(email, password);

        // Deletes the user and returns appropriate response
        if (isAuthenticated && userService.deleteUser(email)) {
            return new ResponseEntity<>("User was deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("An error occurred", HttpStatus.UNAUTHORIZED);
        }
    }

    // Endpoint for updating a user's password
    @PatchMapping("/update/password")
    public ResponseEntity<String> updatePassword(@RequestParam String email,
                                                 @RequestParam String password,
                                                 @RequestParam String newPassword) {
        logger.info("Updating password for user with email: " + email);

        // Authenticates the user
        boolean isAuthenticated = userService.authenticateUser(email, password);

        // Updates the password and returns appropriate response
        if (isAuthenticated && userService.changePassword(email, newPassword)) {
            return new ResponseEntity<>("Password changed successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("An error occurred", HttpStatus.UNAUTHORIZED);
        }
    }

    // Endpoint for updating a user's email
    @PatchMapping("/update/email")
    public ResponseEntity<String> updateEmail(@RequestParam String email,
                                              @RequestParam String password,
                                              @RequestParam String newEmail) {
        logger.info("Updating email for user with email: " + email);

        // Authenticates the user
        boolean isAuthenticated = userService.authenticateUser(email, password);

        // Updates the email and returns appropriate response
        if (isAuthenticated && userService.changeEmail(email, newEmail)) {
            return new ResponseEntity<>("Email changed successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("An error occurred", HttpStatus.UNAUTHORIZED);
        }
    }
}