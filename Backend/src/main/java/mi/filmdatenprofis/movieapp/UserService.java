package mi.filmdatenprofis.movieapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean isEmailAlreadyTaken(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        return existingUser.isPresent();
    }

    public User createUser(User userRequest) {
        User user = new User(userRequest.getName(), userRequest.getSurname(), userRequest.getPassword(), userRequest.getEmail());
        return userRepository.save(user);
    }

    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElse(null);

        // Überprüfen, ob ein Benutzer mit der angegebenen E-Mail-Adresse gefunden wurde und das Passwort übereinstimmt
        return user != null && user.getPassword().equals(password);
    }
}

