package mi.filmdatenprofis.movieapp.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    // Unique id of user
    @Id @Schema(hidden = true)
    private ObjectId id;

    // First name of user
    @Schema(example ="John")
    private String name;

    // Last name of user
    @Schema(example ="Doe")
    private String surname;

    // Unique username of user
    @Schema(example ="JohnDoe")
    private String username;

    // Password of user
    @Schema(example="1234")
    private String password;

    // Unique email of user
    @Schema(example="john@doe.net")
    private String email;

    // Profile of user
    @Schema(hidden = true)
    private UserProfile profile;


    // Constructor
    public User(String name, String surname, String username, String password, String email) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.profile = new UserProfile(username);
    }

}


