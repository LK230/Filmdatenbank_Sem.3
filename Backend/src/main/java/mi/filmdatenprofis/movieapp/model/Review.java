package mi.filmdatenprofis.movieapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

// Annotation to indicate this class is a MongoDB document, stored in the "reviews" collection
@Document(collection = "reviews")

// Lombok annotations to create getters, setters, and constructors
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    // Unique identifier for each review document in the MongoDB collection
    @Id
    private ObjectId id;

    // Body of the review
    private String body;

    // Rating of the review (can be null)
    private Integer rating;

    // IMDB ID of the movie
    private String imdbId;

    // Username of user which created the review
    private String createdBy;

    // Date of creation
    private LocalDateTime created;

    // Date of last update
    private LocalDateTime updated;

    // Constructor to create a new Review with a body, optional rating, imdbId, and timestamps
    public Review(String reviewBody, Integer rating, String imdbId, LocalDateTime created, LocalDateTime updated, String email) {
        this.body = reviewBody;
        this.rating = rating;
        this.imdbId = imdbId;
        this.created = created;
        this.updated = updated;
        this.createdBy = email;
    }
}