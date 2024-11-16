package az.edu.turing.msidentity.model.dto.request;


import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(

        @NotBlank String username,
        @NotBlank String password
) {
}
