package az.edu.turing.msauth.controller;

import az.edu.turing.msauth.config.JwtTokenProvider;
import az.edu.turing.msauth.model.JwtResponse;
import az.edu.turing.msauth.model.LoginRequest;
import az.edu.turing.msauth.model.RegisterRequest;
import az.edu.turing.msauth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private JwtTokenProvider tokenProvider;

    private AuthService authService;


    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody LoginRequest loginRequest) {
        return authService.verifyUser(loginRequest.getUsername(), loginRequest.getPassword())
                .map(isValid -> {
                    if (isValid) {
                        String accessToken = tokenProvider.generateAccessToken(loginRequest.getUsername());
                        String refreshToken = tokenProvider.generateRefreshToken(loginRequest.getUsername());
                        return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
                    } else {
                        return ResponseEntity.status(401).body("Invalid credentials");
                    }
                });
    }


    @PostMapping("/register")
    public Mono<ResponseEntity<?>> register(@RequestBody RegisterRequest registerRequest) {
        return authService.registerUser(registerRequest)
                .map(isRegistered -> {
                    if (isRegistered) {

                        String accessToken = tokenProvider.generateAccessToken(registerRequest.getUsername());
                        String refreshToken = tokenProvider.generateRefreshToken(registerRequest.getUsername());
                        return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
                    } else {
                        return ResponseEntity.status(400).body("User registration failed");
                    }
                });
    }
}
