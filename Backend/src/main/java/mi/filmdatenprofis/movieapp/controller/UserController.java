package mi.filmdatenprofis.movieapp.controller;


import mi.filmdatenprofis.movieapp.model.User;
import mi.filmdatenprofis.movieapp.model.UserProfile;
import mi.filmdatenprofis.movieapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint to create a new user. JSON Body includes name, surname, username and email
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {

        // Check if the provided email or username is already taken
        if (userService.isEmailAlreadyTaken(user.getEmail()) || userService.isUsernameAlreadyTaken(user.getUsername())) {
            return new ResponseEntity<>("The entered e-mail address or username is already taken", HttpStatus.BAD_REQUEST);
        }

        // Check if email is in valid format
        if(!userService.isValidEmail(user.getEmail())) {
            return new ResponseEntity<>("The entered e-mail address is not valid", HttpStatus.BAD_REQUEST);
        }

        // Create the user if email and username are not already taken
        userService.createUser(user);
        return new ResponseEntity<>("User was created successfully!", HttpStatus.CREATED);

    }

    // Endpoint to handle login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {

        // Authenticate user credentials
        boolean isAuthenticated = userService.authenticateUser(email, password);

        // Return response based on authentication result
        if (isAuthenticated) {
            return new ResponseEntity<>("Login successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Wrong email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    // Endpoint to get user profile by username
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserProfile(@RequestParam String username,
                                            @RequestParam String email,
                                            @RequestParam String password) {
        if(userService.authenticateUser(email, password)) {
            return new ResponseEntity<Optional<UserProfile>>(userService.userProfile(username), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("Failed to load profile", HttpStatus.NOT_FOUND);
        }

    }

    // Endpoint to add a movie to a users favorites
    @PostMapping("/favorites/add")
    public ResponseEntity<?> addToFavorites(@RequestParam String username, @RequestParam String imdbId) {

        //Adds the movie with the given imdbId to favorites list
        if (userService.addFavorites(username, imdbId)) {
            return new ResponseEntity<>("Movie added to favorites successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error: Wrong username, movie or movie is already in favorites", HttpStatus.BAD_REQUEST);
        }

    }

    // Endpoint to remove a movie from a users favorites
    @DeleteMapping("/favorites/remove")
    public ResponseEntity<?> removeFromFavorites(@RequestParam String username, @RequestParam String imdbId) {

        //Removes the movie with the given imdbId from favorites list
        if (userService.removeFavorites(username, imdbId)) {
            return new ResponseEntity<>("Movie removed from favorites successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error: Wrong username, movie or movie is not in favorites", HttpStatus.BAD_REQUEST);
        }

    }

    // Endpoint to delete an user.
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String email, @RequestParam String password) {

        // Authenticate user credentials
        boolean isAuthenticated = userService.authenticateUser(email, password);

        // Return response based on authentication and delete result
        if (isAuthenticated && userService.deleteUser(email)) {
                return new ResponseEntity<>("User was deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("An error occured", HttpStatus.UNAUTHORIZED);
        }

    }

    //Endpoint to handle change password request
    @PatchMapping("/update/password")
    public ResponseEntity<String> updatePassword(@RequestParam String email,
                                                 @RequestParam String password,
                                                 @RequestParam String newPassword) {

        // Authenticate user credentials
        boolean isAuthenticated = userService.authenticateUser(email, password);

        // Return response based on authentication and changing result
        if (isAuthenticated && userService.changePassword(email, newPassword)) {
            return new ResponseEntity<>("Password changed succesfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("An error occured", HttpStatus.UNAUTHORIZED);
        }

    }

    //Endpoint to handle change email request
    @PatchMapping("/update/email")
    public ResponseEntity<String> updateEmail(@RequestParam String email,
                                              @RequestParam String password,
                                              @RequestParam String newEmail) {

        // Authenticate user credentials
        boolean isAuthenticated = userService.authenticateUser(email, password);

        // Return response based on authentication and changing result
        if (isAuthenticated && userService.changeEmail(email, newEmail)) {
            return new ResponseEntity<>("Email changed succesfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("An error occured", HttpStatus.UNAUTHORIZED);
        }

    }
}