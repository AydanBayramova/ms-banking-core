package az.edu.turing.msidentity.controller;

import az.edu.turing.msidentity.model.dto.request.LoginRequest;
import az.edu.turing.msidentity.model.dto.response.AuthResponse;
import az.edu.turing.msidentity.model.dto.response.LoginResponse;
import az.edu.turing.msidentity.model.dto.response.RegisterResponse;
import az.edu.turing.msidentity.service.impl.LoginAttemptService;
import az.edu.turing.msidentity.service.inter.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import az.edu.turing.msidentity.model.dto.request.RegisterRequest;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final LoginAttemptService loginAttemptService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        return authService.registerAccount(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
            ResponseEntity<LoginResponse> response = authService.loginUser(loginRequest);
            return response;
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
        String refreshToken = authorizationHeader.replace("Bearer ", "");
        authService.logout(refreshToken);
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
        return authService.refreshToken(authorizationHeader.replace("Bearer ", ""));
    }
}
