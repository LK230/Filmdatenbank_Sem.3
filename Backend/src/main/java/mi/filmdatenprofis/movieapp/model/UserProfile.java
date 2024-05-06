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
    @Id
    private ObjectId id;
    private String username;
    private List<Movie> favorites;
    private List<Review> reviews;

   public UserProfile(String username) {
        this.username = username;
        favorites = new ArrayList<>();
        reviews = new ArrayList<>();
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

}
