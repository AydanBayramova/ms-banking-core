package az.edu.turing.msidentity.service.inter;

import az.edu.turing.msidentity.model.dto.request.LoginRequest;
import az.edu.turing.msidentity.model.dto.request.RegisterRequest;
import az.edu.turing.msidentity.model.dto.response.AuthResponse;
import az.edu.turing.msidentity.model.dto.response.LoginResponse;
import az.edu.turing.msidentity.model.dto.response.RegisterResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<RegisterResponse> registerAccount(RegisterRequest registerRequest);

    ResponseEntity<LoginResponse> loginUser(LoginRequest loginRequest);

    void logout(String refreshToken);

    ResponseEntity<AuthResponse> refreshToken(String refreshToken);
}
