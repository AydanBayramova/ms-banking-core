package az.edu.turing.msidentity.model.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class AuthRequest {

    private String username;
    private String password;
}
