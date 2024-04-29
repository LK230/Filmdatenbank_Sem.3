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

    private LocalDateTime created;

    private LocalDateTime updated;

    // Constructor to create a new Review with a body, optional rating, imdbId, and timestamps
    public Review(String body, Integer rating, String imdbId, LocalDateTime created, LocalDateTime updated) {
        this.body = body;
        this.rating = rating;
        this.imdbId = imdbId;
        this.created = created;
        this.updated = updated;
    }
}