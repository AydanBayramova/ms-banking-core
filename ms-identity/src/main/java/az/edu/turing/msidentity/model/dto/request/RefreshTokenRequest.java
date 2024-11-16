package az.edu.turing.msidentity.model.dto.request;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RefreshTokenRequest {
    private String refreshToken;
}
