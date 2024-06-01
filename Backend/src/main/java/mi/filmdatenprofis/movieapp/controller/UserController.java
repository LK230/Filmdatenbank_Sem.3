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

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        logger.info("Creating user with email: " + user.getEmail());
        if (userService.isEmailAlreadyTaken(user.getEmail()) || userService.isUsernameAlreadyTaken(user.getUsername())) {
            return new ResponseEntity<>("The entered e-mail address or username is already taken", HttpStatus.BAD_REQUEST);
        }

        if(!userService.isValidEmail(user.getEmail())) {
            return new ResponseEntity<>("The entered e-mail address is not valid", HttpStatus.BAD_REQUEST);
        }

        userService.createUser(user);
        return new ResponseEntity<>("User was created successfully!", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
        logger.info("User login attempt with email: " + email);
        boolean isAuthenticated = userService.authenticateUser(email, password);

        if (isAuthenticated) {
            return new ResponseEntity<>("Login successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Wrong email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserProfile(@RequestParam String username,
                                            @RequestParam String email,
                                            @RequestParam String password) {
        logger.info("Getting user profile for username: " + username);
        if(userService.authenticateUser(email, password)) {
            return new ResponseEntity<Optional<UserProfile>>(userService.userProfile(username), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("Failed to load profile", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/favorites/add")
    public ResponseEntity<?> addToFavorites(@RequestParam String username, @RequestParam String imdbId) {
        logger.info("Adding movie with ID: " + imdbId + " to favorites for user: " + username);
        if (userService.addFavorites(username, imdbId)) {
            return new ResponseEntity<>("Movie added to favorites successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error: Wrong username, movie or movie is already in favorites", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/favorites/remove")
    public ResponseEntity<?> removeFromFavorites(@RequestParam String username, @RequestParam String imdbId) {
        logger.info("Removing movie with ID: " + imdbId + " from favorites for user: " + username);
        if (userService.removeFavorites(username, imdbId)) {
            return new ResponseEntity<>("Movie removed from favorites successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error: Wrong username, movie or movie is not in favorites", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String email, @RequestParam String password) {
        logger.info("Deleting user with email: " + email);
        boolean isAuthenticated = userService.authenticateUser(email, password);

        if (isAuthenticated && userService.deleteUser(email)) {
            return new ResponseEntity<>("User was deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("An error occurred", HttpStatus.UNAUTHORIZED);
        }
    }

    @PatchMapping("/update/password")
    public ResponseEntity<String> updatePassword(@RequestParam String email,
                                                 @RequestParam String password,
                                                 @RequestParam String newPassword) {
        logger.info("Updating password for user with email: " + email);
        boolean isAuthenticated = userService.authenticateUser(email, password);

        if (isAuthenticated && userService.changePassword(email, newPassword)) {
            return new ResponseEntity<>("Password changed successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("An error occurred", HttpStatus.UNAUTHORIZED);
        }
    }

    @PatchMapping("/update/email")
    public ResponseEntity<String> updateEmail(@RequestParam String email,
                                              @RequestParam String password,
                                              @RequestParam String newEmail) {
        logger.info("Updating email for user with email: " + email);
        boolean isAuthenticated = userService.authenticateUser(email, password);

        if (isAuthenticated && userService.changeEmail(email, newEmail)) {
            return new ResponseEntity<>("Email changed successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("An error occurred", HttpStatus.UNAUTHORIZED);
        }
    }
}