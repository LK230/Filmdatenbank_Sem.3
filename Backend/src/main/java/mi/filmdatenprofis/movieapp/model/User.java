package mi.filmdatenprofis.movieapp.model;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Import;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class User {

    @Id
    private ObjectId id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private UserProfile profile;


    public User(String name, String surname, String username, String password, String email) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.profile = new UserProfile(username);
    }

}


