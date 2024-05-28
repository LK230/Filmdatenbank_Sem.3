package mi.filmdatenprofis.movieapp.service;

import mi.filmdatenprofis.movieapp.model.Movie;
import mi.filmdatenprofis.movieapp.model.User;
import mi.filmdatenprofis.movieapp.model.UserProfile;
import mi.filmdatenprofis.movieapp.repository.MovieRepository;
import mi.filmdatenprofis.movieapp.repository.UserProfileRepository;
import mi.filmdatenprofis.movieapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    //method to check if a given email is already taken
    public boolean isEmailAlreadyTaken(String email) {
        Optional<User> existingUser = userRepository.findByEmailIgnoreCase(email);
        return existingUser.isPresent();
    }

    //method to check if a given username is already taken
    public boolean isUsernameAlreadyTaken(String username) {
        Optional<User> existingUser = userRepository.findByUsernameIgnoreCase(username);
        return existingUser.isPresent();
    }

    // Method to validate email format
    public boolean isValidEmail(String email) {

        // If the email is null, it is not valid
        if (email == null) {
            return false;
        }

        // Regular expression to check email format
        // ^[\\w._%+-]+        : Start with one or more word characters (letters, digits, underscores), dots, underscores, percent signs, plus signs, or hyphens.
        // @[\\w.-]+           : Followed by the '@' symbol and one or more word characters, dots, or hyphens.
        // \\.[a-zA-Z]{2,}$    : Ends with a dot and two or more alphabetic characters (the top-level domain).
        String emailRegex = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

        // Return true if the email matches the regular expression, otherwise false
        return Pattern.matches(emailRegex, email);
    }

    //method to create a new user and save it to database
    public User createUser(User userRequest) {
        User user = new User(userRequest.getName(), userRequest.getSurname(), userRequest.getUsername(), userRequest.getPassword(), userRequest.getEmail());
        userProfileRepository.save(user.getProfile());
        return userRepository.save(user);
    }

    //method to authenticate user by email/password combination
    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElse(null);
        if(user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }

    //method to return profile data by its username
    public Optional<UserProfile> userProfile(String username) {
        return userProfileRepository.findByUsernameIgnoreCase(username);
    }

    //adds a movie to users favorites
    public boolean addFavorites (String username, String imdbId) {

        //search user and movie by given username and imdbId
        User user = userRepository.findByUsernameIgnoreCase(username).orElse(null);
        Movie movie = movieRepository.findMovieByImdbId(imdbId).orElse(null);

        //adds movie to favorites if user and movie exists and movie isn't already in favorites
        if(user != null && movie != null) {
            if(!user.getProfile().getFavorites().stream()
                    .anyMatch(movieToCheck -> movie.getImdbId().equals(imdbId))) {

                user.getProfile().getFavorites().add(movie);

                //save user and profile to database
                userRepository.save(user);
                userProfileRepository.save(user.getProfile());
                return true;
            }
        }
        return false;

    }

    //remove a movie from a users favorites
    public boolean removeFavorites(String username, String imdbId) {

        //search user and movie by given username and imdbId
        User user = userRepository.findByUsernameIgnoreCase(username).orElse(null);
        Movie movie = movieRepository.findMovieByImdbId(imdbId).orElse(null);

        //removes movie from favorites if user and movie exists and movie is in favorites
        if(user != null && movie != null) {

            //check if movie is in favorites
            if(user.getProfile().getFavorites().stream()
                    .anyMatch(movieToCheck -> movie.getImdbId().equals(imdbId))) {

                //remove from list
                user.getProfile().getFavorites().removeIf(movieToRemove -> movie.getImdbId().equals((imdbId)));

                //save profile and user to database
                userRepository.save(user);
                userProfileRepository.save(user.getProfile());
                return true;
            }

        }
        return false;
    }

    //method for deleting a existing user
    public boolean deleteUser(String email) {

        //Get user and profile which are about to get deleted
        User user = userRepository.findByEmailIgnoreCase(email).orElse(null);
        UserProfile userProfile = userProfileRepository.findByUsernameIgnoreCase(user.getUsername()).orElse(null);

        //When User and profile are found -> delete them from database
        if(user != null && userProfile != null) {
            userProfileRepository.delete(userProfile);
            userRepository.delete(user);
            return true;
        }
        else {
            return false;
        }
    }

    //method to change password on existing user
    public boolean changePassword(String email, String newPassword) {

        //Get user which is about to get changed
        User user = userRepository.findByEmailIgnoreCase(email).orElse(null);

        //When User is found -> change password and save it to database
        if(user != null ) {
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        else {
            return false;
        }
    }

    //method to change email on existing user
    public boolean changeEmail(String email, String newEmail) {

        //Get user which is about to get changed
        User user = userRepository.findByEmailIgnoreCase(email).orElse(null);

        //When User is found -> change email and save it to database
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
