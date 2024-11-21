package az.edu.turing.msidentity.service.impl;

import az.edu.turing.msidentity.client.*;
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

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final PasswordEncoder passwordEncoderUtil;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;
    private final AccountClient accountClient;


    @Override
    public UserEntity registerAccount(RegisterRequest registerRequest) {

        UserEntity user = userMapper.requestToEntity(registerRequest);
        user.setPassword(passwordEncoderUtil.encode(registerRequest.password()));
        user.setId(UUID.randomUUID());
        user.setEnabled(true);
        UserEntity userEntity = userRepository.save(user);
        AccountRequest accountRequest = prepareAccountRequest(user, registerRequest.password());
        ResponseEntity<String> response = createAccount(String.valueOf(userEntity.getId()), accountRequest);
        if (response != null && response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Account Created: " + response.getBody());
        } else {
            throw new RuntimeException("Account can't be created!");
        }
        return userEntity;
    }

    private AccountRequest prepareAccountRequest(UserEntity user, String rawPassword) {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setUsername(user.getUsername());
        accountRequest.setPassword(rawPassword);
        accountRequest.setEmail(user.getEmail());
        accountRequest.setBank(BankType.KAPITAL_BANK);
        accountRequest.setType(AccountType.CURRENT);
        accountRequest.setCurrency(CurrencyType.USD);

        return accountRequest;
    }


    private ResponseEntity<String> createAccount(String userId, AccountRequest accountRequest) {
        try {
            return accountClient.createAccount(userId, accountRequest);
        } catch (Exception e) {

            System.err.println("Feign Client error: " + e.getMessage());
            throw new RuntimeException("Happen error when account created!", e);
        }
    }

    @Override
    public ResponseEntity<LoginResponse> loginUser(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByUsername(loginRequest.username()).orElseThrow(() -> new RuntimeException("Username or password is incorrect!"));

        if (!passwordEncoderUtil.matches(loginRequest.password(), user.getPassword())) {
            return ResponseEntity.badRequest().body(new LoginResponse("Invalid credentials"));
        }

        String token = jwtTokenProvider.createAccessToken(loginRequest.username());
        String refreshToken = jwtTokenProvider.createRefreshToken(loginRequest.username());

        String redisKey = "refreshToken:" + loginRequest.username();
        redisTemplate.opsForValue().set(redisKey, refreshToken);


        LoginResponse loginResponse = LoginResponse.builder()
                .message("Login successful")
                .username(loginRequest.username())
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
