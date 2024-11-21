package az.edu.turing.msidentity.model.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {

    @NotNull
    private UUID id;

    @NotBlank
    private String username;

    @NotBlank
    private String message;

    public RegisterResponse(String message) {
        this.message = message;
    }
}
