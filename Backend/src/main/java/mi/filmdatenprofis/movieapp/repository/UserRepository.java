package mi.filmdatenprofis.movieapp.repository;

import mi.filmdatenprofis.movieapp.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    //method to find a user by its email
    Optional<User> findByEmailIgnoreCase(String email);

    //method to find a user by its username
    Optional<User> findByUsernameIgnoreCase(String username);

}
