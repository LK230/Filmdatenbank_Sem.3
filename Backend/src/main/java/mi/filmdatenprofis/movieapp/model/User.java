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

    @Id @Schema(hidden = true)
    private ObjectId id;

    @Schema(example ="John")
    private String name;

    @Schema(example ="Doe")
    private String surname;

    @Schema(example ="JohnDoe")
    private String username;

    @Schema(example="1234")
    private String password;

    @Schema(example="john@doe.net")
    private String email;

    @Schema(hidden = true)
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


