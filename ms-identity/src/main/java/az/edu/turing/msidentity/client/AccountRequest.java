package az.edu.turing.msidentity.client;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@Data
@RequiredArgsConstructor
public class AccountRequest {

    private String username;

    private String password;

    private String email;
}
