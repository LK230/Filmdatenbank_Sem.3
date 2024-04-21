package mi.filmdatenprofis.movieapp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {

        // Überprüfen, ob die E-Mail bereits verwendet wird
        if (userService.isEmailAlreadyTaken(user.getEmail())) {
            return new ResponseEntity<>("The entered e-mail address is already taken", HttpStatus.BAD_REQUEST);
        }

        userService.createUser(user);
        return new ResponseEntity<>("User was created successfully!", HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> userLogin) {
        String email = userLogin.get("email");
        String password = userLogin.get("password");

        // Überprüfen, ob die angegebenen Anmeldeinformationen korrekt sind
        boolean isAuthenticated = userService.authenticateUser(email, password);

        if (isAuthenticated) {
            return new ResponseEntity<>("Login successfull!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Wrong email or password", HttpStatus.UNAUTHORIZED);
        }
    }
}
