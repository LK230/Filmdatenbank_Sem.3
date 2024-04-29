package mi.filmdatenprofis.movieapp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private ObjectId id;
    private String name;
    private String surname;
    private String username;

    //@JsonIgnore
    private String password;

    private String email;
    private List<Movie> favorites;
    private List<Review> reviews;

    public User(String name, String surname, String username, String password, String email) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.reviews = new ArrayList<>();
        this.favorites = new ArrayList<>();
    }
    public void addReview(Review review) {
        this.reviews.add(review);
    }

}
