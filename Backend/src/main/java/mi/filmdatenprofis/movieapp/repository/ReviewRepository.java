package mi.filmdatenprofis.movieapp.repository;

// Importing necessary libraries and classes
import mi.filmdatenprofis.movieapp.model.Review;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// Annotation to indicate this is a Repository
@Repository
public interface ReviewRepository extends MongoRepository<Review, ObjectId> {

}
