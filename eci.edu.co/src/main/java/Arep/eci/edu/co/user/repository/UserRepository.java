package Arep.eci.edu.co.user.repository;

import Arep.eci.edu.co.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    Optional<User> findBySub(String sub);
    
    boolean existsBySub(String sub);
}
