package az.edu.turing.msidentity.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class AuthResponse {

    private String accessToken;
    private String refreshToken;

}
