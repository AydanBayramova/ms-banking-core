package az.edu.turing.msidentity.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-account", path = "/account")
public interface AccountClient {
    @PostMapping("/add/{userId}")
    ResponseEntity<String> createAccount(@RequestBody AccountRequest request, @PathVariable String userId);
}
