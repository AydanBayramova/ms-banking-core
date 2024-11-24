package az.edu.turing.msidentity.service.impl;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoginAttemptService {
    private final int MAX_ATTEMPTS = 3;
    private final Map<String, Integer> attempts = new HashMap<>();

    public void loginSucceeded(String key){
        attempts.remove(key);
    }

    public void loginFailed(String key){
        Integer attemptCount = attempts.getOrDefault(key, 0);
        attemptCount++;
        attempts.put(key, attemptCount);
    }
    public boolean isBlocked(String key){
        return attempts.getOrDefault(key, 0) > MAX_ATTEMPTS;
    }
}
