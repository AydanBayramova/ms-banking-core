package az.edu.turing.msidentity.controller;


import az.edu.turing.msidentity.model.dto.request.AuthRequest;
import az.edu.turing.msidentity.model.dto.request.UserRequest;
import az.edu.turing.msidentity.model.dto.response.AuthResponse;
import az.edu.turing.msidentity.model.dto.response.UserResponse;
import az.edu.turing.msidentity.repository.UserRepository;
import az.edu.turing.msidentity.service.inter.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;


    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        return userService.createUser(request);
    }



}
