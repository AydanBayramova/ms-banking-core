package az.edu.turing.msidentity.service.impl;

import az.edu.turing.msidentity.entity.UserEntity;
import az.edu.turing.msidentity.mapper.UserMapper;
import az.edu.turing.msidentity.model.dto.request.LoginRequest;
import az.edu.turing.msidentity.model.dto.request.RegisterRequest;
import az.edu.turing.msidentity.model.dto.response.AuthResponse;
import az.edu.turing.msidentity.model.dto.response.LoginResponse;
import az.edu.turing.msidentity.model.dto.response.RegisterResponse;
import az.edu.turing.msidentity.repository.UserRepository;
import az.edu.turing.msidentity.securityconfig.JwtTokenProvider;
import az.edu.turing.msidentity.service.inter.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final PasswordEncoder passwordEncoderUtil;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public ResponseEntity<RegisterResponse> registerAccount(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.username())) {
            return ResponseEntity.badRequest().body(new RegisterResponse("Username is already taken!"));
        }
        UserEntity user = userMapper.requestToEntity(registerRequest);
        user.setPassword(passwordEncoderUtil.encode(registerRequest.password()));
        user.setId(UUID.randomUUID());
        user.setRoles(Set.of("USER"));
        user.setEnabled(true);
          UserEntity userEntity = userRepository.save(user);
        new RegisterResponse();
        return ResponseEntity.ok(RegisterResponse.builder().id(userEntity.getId()).username(userEntity.getUsername()).message("Registered successfully").build());
    }

    @Override
    public ResponseEntity<LoginResponse> loginUser(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Username or password is incorrect!"));

        if (!passwordEncoderUtil.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(new LoginResponse("Invalid credentials"));
        }

        String token = jwtTokenProvider.createAccessToken(loginRequest.getUsername(), user.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(loginRequest.getUsername(), user.getRoles());

        String redisKey = "refreshToken:" + loginRequest.getUsername();
        redisTemplate.opsForValue().set(redisKey, refreshToken);

        LoginResponse loginResponse = LoginResponse.builder()
                .message("Login successful")
                .username(loginRequest.getUsername())
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();

        return ResponseEntity.ok(loginResponse);
    }

    @Override
    public void logout(String refreshToken) {
        String username = jwtTokenProvider.getUsername(refreshToken);
        String redisKey = "refreshToken:" + username;
        redisTemplate.delete(redisKey);
        System.out.println(redisTemplate.opsForValue().get(redisKey));
    }

    @Override
    public ResponseEntity<AuthResponse> refreshToken(String refreshToken) {
        AuthResponse authResponse = jwtTokenProvider.refreshToken(refreshToken);
        return ResponseEntity.ok(authResponse);
    }
}
