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
        Optional<User> existingUser = userRepository.findByEmail(email);
        return existingUser.isPresent();
    }

    //method to check if a given username is already taken
    public boolean isUsernameAlreadyTaken(String username) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        return existingUser.isPresent();
    }

    //method to create a new user and save it to database
    public User createUser(User userRequest) {
        User user = new User(userRequest.getName(), userRequest.getSurname(), userRequest.getUsername(), userRequest.getPassword(), userRequest.getEmail());
        userProfileRepository.save(user.getProfile());
        return userRepository.save(user);
    }

    //method to authenticate user by email/password combination
    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElse(null);
        if(user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }

    //method to return profile data by its username
    public Optional<UserProfile> userProfile(String username) {
        return userProfileRepository.findByUsername(username);
    }

    //adds a movie to users favorites
    public boolean addFavorites (String username, String imdbId) {

        //search user and movie by given username and imdbId
        User user = userRepository.findByUsername(username).orElse(null);
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
        User user = userRepository.findByUsername(username).orElse(null);
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
}
