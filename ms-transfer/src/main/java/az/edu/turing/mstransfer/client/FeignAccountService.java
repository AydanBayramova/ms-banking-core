//package az.edu.turing.mstransfer.client;
//
//import az.edu.turing.mstransfer.model.dto.AccountDto;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.math.BigDecimal;
//
//@FeignClient(name = "ms-account", path = "/accounts")
//public interface FeignAccountService {
//
//    @GetMapping("/byNumber/{accNumber}")
//    ResponseEntity<AccountDto> getAccountByNumber(@PathVariable String accountNumber);
//
//    @PutMapping("/balance")
//    ResponseEntity<Float> changeBalance(
//            @RequestParam String fromAccount,
//            @RequestParam String toAccount,
//            @RequestParam BigDecimal amount
//    );
//}
