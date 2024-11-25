package az.edu.turing.msidentity.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    String username;

    @NotBlank
    String password;

}
