package az.edu.turing.msidentity.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRequest {

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;
}
