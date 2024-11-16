package az.edu.turing.msidentity.repository;


import az.edu.turing.msidentity.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, UUID> {

    UserEntity findByUsername(String username);
}
