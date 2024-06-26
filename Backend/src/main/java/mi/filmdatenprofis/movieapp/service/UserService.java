package mi.filmdatenprofis.movieapp.service;

import mi.filmdatenprofis.movieapp.model.Movie;
import mi.filmdatenprofis.movieapp.model.User;
import mi.filmdatenprofis.movieapp.model.UserProfile;
import mi.filmdatenprofis.movieapp.repository.MovieRepository;
import mi.filmdatenprofis.movieapp.repository.UserProfileRepository;
import mi.filmdatenprofis.movieapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * Checks if the given email is already taken by an existing user.
     */
    public boolean isEmailAlreadyTaken(String email) {
        logger.info("Checking if email is already taken: " + email);
        Optional<User> existingUser = userRepository.findByEmailIgnoreCase(email);
        return existingUser.isPresent();
    }

    /**
     * Checks if the given username is already taken by an existing user.
     */
    public boolean isUsernameAlreadyTaken(String username) {
        logger.info("Checking if username is already taken: " + username);
        Optional<User> existingUser = userRepository.findByUsernameIgnoreCase(username);
        return existingUser.isPresent();
    }

    /**
     * Validates if the given email is in a valid format.
     */
    public boolean isValidEmail(String email) {
        logger.info("Validating email: " + email);
        if (email == null) {
            return false;
        }
        String emailRegex = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    /**
     * Creates a new user with the provided details.
     */
    public User createUser(User userRequest) {
        logger.info("Creating user with email: " + userRequest.getEmail());
        User user = new User(userRequest.getName(), userRequest.getSurname(), userRequest.getUsername(), userRequest.getPassword(), userRequest.getEmail());
        userProfileRepository.save(user.getProfile());
        return userRepository.save(user);
    }

    /**
     * Authenticates a user based on the provided email and password.
     */
    public boolean authenticateUser(String email, String password) {
        logger.info("Authenticating user with email: " + email);
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElse(null);
        if(user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }

    /**
     * Retrieves the user profile for the given username.
     */
    public Optional<UserProfile> userProfile(String username) {
        logger.info("Fetching user profile for username: " + username);
        return userProfileRepository.findByUsernameIgnoreCase(username);
    }

    public boolean addFavorites(String username, String imdbId) {
        logger.info("Adding movie with ID: " + imdbId + " to favorites for user: " + username);

        // Find user and movie
        User user = userRepository.findByUsernameIgnoreCase(username).orElse(null);
        Movie movie = movieRepository.findMovieByImdbId(imdbId).orElse(null);

        if (user != null && movie != null) {
            UserProfile profile = user.getProfile();

            // Check if the movie is already in favorites
            if (profile.getFavorites().stream().noneMatch(fav -> fav.getImdbId().equals(imdbId))) {
                profile.getFavorites().add(movie);
                userProfileRepository.save(profile); // Save profile first

                // Update user with the latest profile changes
                user.setProfile(profile);
                userRepository.save(user); // Save user

                return true;
            }
        }
        return false;
    }

    /**
     * Removes a movie with the given IMDb ID from the favorites list of the user.
     */
    public boolean removeFavorites(String username, String imdbId) {
        logger.info("Removing movie with ID: " + imdbId + " from favorites for user: " + username);

        // Find user and movie
        User user = userRepository.findByUsernameIgnoreCase(username).orElse(null);
        Movie movie = movieRepository.findMovieByImdbId(imdbId).orElse(null);

        if(user != null && movie != null) {

            // If movie is in favorites list delete it
            if(user.getProfile().getFavorites().stream()
                    .anyMatch(movieToCheck -> movie.getImdbId().equals(imdbId))) {
                user.getProfile().getFavorites().removeIf(movieToRemove -> movie.getImdbId().equals((imdbId)));

                // Save changes to database
                userRepository.save(user);
                userProfileRepository.save(user.getProfile());
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a user based on the provided email.
     */
    public boolean deleteUser(String email) {
        logger.info("Deleting user with email: " + email);

        // Find user and userprofile
        User user = userRepository.findByEmailIgnoreCase(email).orElse(null);
        UserProfile userProfile = userProfileRepository.findByUsernameIgnoreCase(user.getUsername()).orElse(null);

        // If user and profile was found delete it from database
        if(user != null && userProfile != null) {
            userProfileRepository.delete(userProfile);
            userRepository.delete(user);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Changes the password for a user identified by the provided email.
     */
    public boolean changePassword(String email, String newPassword) {
        logger.info("Changing password for user with email: " + email);

        // Find user
        User user = userRepository.findByEmailIgnoreCase(email).orElse(null);

        if(user != null ) {
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Changes the email address for a user identified by the provided email.
     */
    public boolean changeEmail(String email, String newEmail) {
        logger.info("Changing email for user with email: " + email);

        // Find user
        User user = userRepository.findByEmailIgnoreCase(email).orElse(null);

        // If user was found change email and save to database
        if(user != null && !isEmailAlreadyTaken(newEmail)) {
            user.setEmail(newEmail);
            userRepository.save(user);
            return true;
        }
        else {
            return false;
        }
    }
}