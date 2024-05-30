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

    public boolean isEmailAlreadyTaken(String email) {
        logger.info("Checking if email is already taken: " + email);
        Optional<User> existingUser = userRepository.findByEmailIgnoreCase(email);
        return existingUser.isPresent();
    }

    public boolean isUsernameAlreadyTaken(String username) {
        logger.info("Checking if username is already taken: " + username);
        Optional<User> existingUser = userRepository.findByUsernameIgnoreCase(username);
        return existingUser.isPresent();
    }

    public boolean isValidEmail(String email) {
        logger.info("Validating email: " + email);
        if (email == null) {
            return false;
        }
        String emailRegex = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    public User createUser(User userRequest) {
        logger.info("Creating user with email: " + userRequest.getEmail());
        User user = new User(userRequest.getName(), userRequest.getSurname(), userRequest.getUsername(), userRequest.getPassword(), userRequest.getEmail());
        userProfileRepository.save(user.getProfile());
        return userRepository.save(user);
    }

    public boolean authenticateUser(String email, String password) {
        logger.info("Authenticating user with email: " + email);
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElse(null);
        if(user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }

    public Optional<UserProfile> userProfile(String username) {
        logger.info("Fetching user profile for username: " + username);
        return userProfileRepository.findByUsernameIgnoreCase(username);
    }

    public boolean addFavorites (String username, String imdbId) {
        logger.info("Adding movie with ID: " + imdbId + " to favorites for user: " + username);
        User user = userRepository.findByUsernameIgnoreCase(username).orElse(null);
        Movie movie = movieRepository.findMovieByImdbId(imdbId).orElse(null);
        if(user != null && movie != null) {
            if(!user.getProfile().getFavorites().stream()
                    .anyMatch(movieToCheck -> movie.getImdbId().equals(imdbId))) {
                user.getProfile().getFavorites().add(movie);
                userRepository.save(user);
                userProfileRepository.save(user.getProfile());
                return true;
            }
        }
        return false;
    }

    public boolean removeFavorites(String username, String imdbId) {
        logger.info("Removing movie with ID: " + imdbId + " from favorites for user: " + username);
        User user = userRepository.findByUsernameIgnoreCase(username).orElse(null);
        Movie movie = movieRepository.findMovieByImdbId(imdbId).orElse(null);
        if(user != null && movie != null) {
            if(user.getProfile().getFavorites().stream()
                    .anyMatch(movieToCheck -> movie.getImdbId().equals(imdbId))) {
                user.getProfile().getFavorites().removeIf(movieToRemove -> movie.getImdbId().equals((imdbId)));
                userRepository.save(user);
                userProfileRepository.save(user.getProfile());
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String email) {
        logger.info("Deleting user with email: " + email);
        User user = userRepository.findByEmailIgnoreCase(email).orElse(null);
        UserProfile userProfile = userProfileRepository.findByUsernameIgnoreCase(user.getUsername()).orElse(null);
        if(user != null && userProfile != null) {
            userProfileRepository.delete(userProfile);
            userRepository.delete(user);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean changePassword(String email, String newPassword) {
        logger.info("Changing password for user with email: " + email);
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

    public boolean changeEmail(String email, String newEmail) {
        logger.info("Changing email for user with email: " + email);
        User user = userRepository.findByEmailIgnoreCase(email).orElse(null);
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