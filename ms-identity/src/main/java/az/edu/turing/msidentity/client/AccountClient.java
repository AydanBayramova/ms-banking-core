package az.edu.turing.msidentity.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-account", url = "localhost:8095")
public interface AccountClient {

    @DeleteMapping("/account/all/{userId}")
    ResponseEntity<HttpStatus> deleteAllAccounts(@PathVariable(name = "userId") String userId);

}
