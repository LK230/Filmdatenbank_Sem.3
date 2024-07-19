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

/**
 * Service class for managing user profiles and their interactions such as adding or removing favorites.
 */
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
     * Fetches the user profile by email and password.
     *
     * This method retrieves a user by their email and checks if the provided password matches.
     *
     * @param email the email of the user
     * @param password the password of the user
     * @return an Optional containing the user if the email and password match, otherwise an empty Optional
     */
    public Optional<User> getUserProfileByEmail(String email, String password) {
        logger.info("Fetching user by email: " + email);
        Optional<User> userOptional = userRepository.findByEmailIgnoreCase(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                return userOptional;
            }
        }
        return Optional.empty();
    }

    /**
     * Fetches the user profile by username.
     *
     * This method retrieves a user's profile using their username.
     *
     * @param username the username of the user
     * @return an Optional containing the user profile if found, otherwise an empty Optional
     */
    public Optional<UserProfile> userProfile(String username) {
        logger.info("Fetching user profile for username: " + username);
        return userProfileRepository.findByUsernameIgnoreCase(username);
    }

    /**
     * Adds a movie to the user's list of favorite movies.
     *
     * This method finds the user by email and the movie by its IMDb ID. If both exist and the movie
     * is not already in the user's favorites, it adds the movie to the favorites list.
     *
     * @param email the email of the user
     * @param imdbId the IMDb ID of the movie
     * @return true if the movie was added to the favorites, false otherwise
     */
    public boolean addFavorites(String email, String imdbId) {
        logger.info("Adding movie with ID: " + imdbId + " to favorites for user: " + email);
        User user = userRepository.findByEmailIgnoreCase(email).orElse(null);
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
     * Removes a movie from the user's list of favorite movies.
     *
     * This method finds the user by email and the movie by its IMDb ID. If both exist and the movie
     * is in the user's favorites, it removes the movie from the favorites list.
     *
     * @param email the email of the user
     * @param imdbId the IMDb ID of the movie
     * @return true if the movie was removed from the favorites, false otherwise
     */
    public boolean removeFavorites(String email, String imdbId) {
        logger.info("Removing movie with ID: " + imdbId + " from favorites for user: " + email);
        User user = userRepository.findByEmailIgnoreCase(email).orElse(null);
        Movie movieToRemove = movieRepository.findMovieByImdbId(imdbId).orElse(null);

        if (user != null && movieToRemove != null) {
            // Check if the movie with imdbId exists in the user's favorites
            if (user.getProfile().getFavorites().stream()
                    .anyMatch(movie -> movie.getImdbId().equals(imdbId))) {

                // Remove the movie from the user's favorites
                user.getProfile().getFavorites().removeIf(movie -> movie.getImdbId().equals(imdbId));

                // Save changes to the repositories
                userRepository.save(user);
                userProfileRepository.save(user.getProfile());
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a user by their email.
     *
     * This method finds the user and their profile by email and deletes them from the database if they exist.
     *
     * @param email the email of the user
     * @return true if the user and their profile were deleted, false otherwise
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