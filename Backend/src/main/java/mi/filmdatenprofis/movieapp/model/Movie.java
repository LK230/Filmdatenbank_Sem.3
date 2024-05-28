package mi.filmdatenprofis.movieapp.model;

// Lombok annotations for getter, setter, constructor generation

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import java.util.List;

// Annotation to indicate this class is a MongoDB document, stored in the "movies" collection
@Document(collection =  "movies")

// Lombok annotations to create getters, setters, and constructors
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Movie {
    // Unique identifier for each movie document in the MongoDB collection
    @Id
    private ObjectId id;

    // IMDB identifier for the movie
    private String imdbId;

    // Title of the movie
    private String title;

    // Release date of the movie
    private String releaseDate;

    //Director of the movie
    private String director;

    //Runtime in minutes of the movie
    private String runtime;

    //Actors in the movie
    private List<String> actors;

    //Short description of the plot
    private String plot;

    // Link to the movie trailer
    private String trailerLink;

    //Total rating of the movie
    private String rating;

    //Amount of reviews from users
    private int reviews;

    // List of genres the movie belongs to
    private List<String> genres;

    // Link to the movie poster image
    private String poster;

    // List of backdrop images for the movie
    private List<String> backdrops;

    // List of reviews associated with the movie
    @DocumentReference
    private List<Review> reviewIds;
}
