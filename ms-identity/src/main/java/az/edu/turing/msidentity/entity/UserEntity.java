package az.edu.turing.msidentity.entity;

import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Document(value = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {
    @Id
    @Indexed(unique = true)
    private UUID id;

    @Field("username")
    private String username;

    @Field("email")
    @Indexed(unique = true)
    private String email;

    private String password;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    @Field("phone_number")
    private String phoneNumber;

    @Field("is_enabled")
    private boolean isEnabled;

}
