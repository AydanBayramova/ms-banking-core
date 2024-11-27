package az.edu.turing.msidentity.repository;

import az.edu.turing.msidentity.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustom {


    private final MongoTemplate mongoTemplate;

    public void updatePasswordByEmail(String email, String newPassword) {
        Query query = new Query(Criteria.where("email").is(email));
        Update update = new Update().set("password", newPassword);

        mongoTemplate.updateFirst(query, update, UserEntity.class);
    }
}
