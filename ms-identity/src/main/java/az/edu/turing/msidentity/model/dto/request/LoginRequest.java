package az.edu.turing.msidentity.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginRequest {

    @NotBlank
    String username;

    @NotBlank
    String password;
}
