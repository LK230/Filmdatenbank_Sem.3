package mi.filmdatenprofis.movieapp.service;

import mi.filmdatenprofis.movieapp.model.Movie;
import mi.filmdatenprofis.movieapp.model.User;
import mi.filmdatenprofis.movieapp.repository.MovieRepository;
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

    public boolean isEmailAlreadyTaken(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        return existingUser.isPresent();
    }

    public boolean isUsernameAlreadyTaken(String username) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        return existingUser.isPresent();
    }

    public User createUser(User userRequest) {
        User user = new User(userRequest.getName(), userRequest.getSurname(), userRequest.getUsername(), userRequest.getPassword(), userRequest.getEmail());
        return userRepository.save(user);
    }

    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElse(null);
        if(user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }

    public Optional<User> userProfile(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean addFavorites (String username, String imdbId) {

        User user = userRepository.findByUsername(username).orElse(null);
        Movie movie = movieRepository.findMovieByImdbId(imdbId).orElse(null);

        if(user != null && movie != null) {
            user.getFavorites().add(movie);
            userRepository.save(user);
            return true;
        }
        return false;

    }

    public boolean removeFavorites(String username, String imdbId) {
        User user = userRepository.findByUsername(username).orElse(null);
        Movie movie = movieRepository.findMovieByImdbId(imdbId).orElse(null);

        if(user != null && movie != null) {
            user.getFavorites().remove(movie);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
