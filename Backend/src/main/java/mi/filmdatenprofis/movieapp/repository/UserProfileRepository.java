package mi.filmdatenprofis.movieapp.repository;


import mi.filmdatenprofis.movieapp.model.UserProfile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfile, ObjectId> {

    //method to find a UserProfile by its username
    Optional<UserProfile> findByUsernameIgnoreCase(String username);
}
