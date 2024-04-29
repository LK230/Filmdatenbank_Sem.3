package mi.filmdatenprofis.movieapp.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    private ObjectId id;
    private String body;
    private String createdBy;
    private String imdbId;

    public Review(String body, String username, String imdbId) {
        this.body = body;
        this.createdBy = username;
        this.imdbId = imdbId;
    }

}
