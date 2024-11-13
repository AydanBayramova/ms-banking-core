package az.edu.turing.msauth.service;

import az.edu.turing.msauth.model.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class AuthService {


    private WebClient.Builder webClientBuilder;


    public Mono<Boolean> verifyUser(String username, String password) {
        return webClientBuilder.build()
                .post()
                .uri("http://ms-identity/identity/verify")
                .bodyValue(Map.of("username", username, "password", password))
                .retrieve()
                .bodyToMono(Boolean.class);
    }


    public Mono<Boolean> registerUser(RegisterRequest registerRequest) {
        return webClientBuilder.build()
                .post()
                .uri("http://ms-identity/identity/register")
                .bodyValue(registerRequest)
                .retrieve()
                .bodyToMono(Boolean.class);
    }
}
