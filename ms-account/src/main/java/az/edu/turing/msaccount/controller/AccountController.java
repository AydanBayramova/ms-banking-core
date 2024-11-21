package az.edu.turing.msaccount.controller;

import az.edu.turing.msaccount.exception.AccountNotFoundException;
import az.edu.turing.msaccount.exception.TransactionFetchException;
import az.edu.turing.msaccount.model.request.AccountRequest;
import az.edu.turing.msaccount.model.response.AccountResponse;
import az.edu.turing.msaccount.model.response.AccountResponseForMsTransfer;
import az.edu.turing.msaccount.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account/")
@Validated
@Slf4j
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "{accId}")
    public ResponseEntity<AccountResponse> fetchAccount(@NotNull @PathVariable(name = "accId") Long accountId) {
        log.info("Fetching account details for id: {}", accountId);

        try {
            AccountResponse accountResponse = service.fetchAccountDetailsWithTransactions(accountId);
            return ResponseEntity.ok(accountResponse);
        } catch (AccountNotFoundException e) {
            log.error("Account not found: {}", accountId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (TransactionFetchException e) {
            log.error("Failed to fetch transactions for account id: {}", accountId);
            return ResponseEntity.ok(e.getAccountResponseWithoutTransactions());
        }
    }

    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AccountResponse> getAllAccounts() {
        log.info("Get all Accounts API triggered.");
        return service.getAllAccounts();
    }

    @PostMapping("add/{userId}")
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody AccountRequest account,
            @PathVariable String userId) {

        log.info("Yeni hesab yaradılır. UserId: {}, Username: {}", userId, account.getUsername());
        return service.createAccount(UUID.fromString(userId), account);
    }



    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountResponse> updateAccount(@RequestParam("id") Long id, @RequestBody AccountRequest account) throws AccountNotFoundException {
        log.info("Updated account details: {} ", account);
        ResponseEntity<AccountResponse> updatedAccount = service.updateAccount(id, account);
        return ResponseEntity.ok(updatedAccount.getBody());
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAccount(Long id) throws AccountNotFoundException {
        service.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("doTrx")
    public Float changeBalance(
            @NotBlank(message = "Source account number is required") @RequestParam("source") String source,
            @NotBlank(message = "Destination account number is required") @RequestParam("destination") String destination,
            @NotNull(message = "Amount cannnot be empty") @RequestParam("amount") Float amount)
            throws AccountNotFoundException {
        log.info("Transaction initiated in Account: {}");
        log.info("Sending {} INR from {} account to {} account", amount, source, destination);
        return service.changeBalance(source, destination, amount);
    }

    @GetMapping("byNumber/{accNumber}")
    public ResponseEntity<AccountResponseForMsTransfer> getAccountByNumber(@PathVariable(name = "accNumber") String accNumber) throws AccountNotFoundException {
        log.info("Get account by account number: {}", accNumber);
        return service.getByNumber(accNumber);
    }
}
