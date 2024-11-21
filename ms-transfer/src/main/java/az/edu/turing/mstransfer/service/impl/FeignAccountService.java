package az.edu.turing.mstransfer.service.impl;

import az.edu.turing.mstransfer.model.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "ms-account", path = "/account")
public interface FeignAccountService {

    @PostMapping("/doTrx")
    BigDecimal changeBalance(@RequestParam("source") String source,
                             @RequestParam("destination") String destination,
                             @RequestParam("amount") Float amount
    );

    @GetMapping("/accounts/{accountNumber}")
    ResponseEntity<AccountDto> getAccountByNumber(@PathVariable("accountNumber") String accountNumber);
}
