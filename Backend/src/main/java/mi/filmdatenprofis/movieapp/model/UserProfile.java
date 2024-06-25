package mi.filmdatenprofis.movieapp.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document(collection="profiles")
@Data
public class UserProfile {

    // Unique id of profile
    @Id
    private ObjectId id;

    // Username which belongs to profile
    private String username;

    // List of favorite movies
    private List<Movie> favorites;

    // List of created reviews
    private List<Review> reviews;

    // Constructor
   public UserProfile(String username) {
        this.username = username;
        favorites = new ArrayList<>();
        reviews = new ArrayList<>();
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

}
