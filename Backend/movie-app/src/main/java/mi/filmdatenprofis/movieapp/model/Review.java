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

    // One to five stars
    private int rating;

    private LocalDateTime created;

    private LocalDateTime updated;

    // Constructor to create a new Review with a body and rating
    public Review(String body, int rating, LocalDateTime created, LocalDateTime updated) {
        this.body = body;
        this.rating = rating;
        this.created = created;
        this.updated = updated;
    }
}