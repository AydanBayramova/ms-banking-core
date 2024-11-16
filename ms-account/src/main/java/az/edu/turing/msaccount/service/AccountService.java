package az.edu.turing.msaccount.service;



import java.util.List;
import java.util.UUID;


import az.edu.turing.msaccount.exception.AccountNotFoundException;
import az.edu.turing.msaccount.model.request.AccountRequest;
import az.edu.turing.msaccount.model.response.AccountResponse;
import az.edu.turing.msaccount.model.response.AccountResponseForMsTransfer;
import org.springframework.http.ResponseEntity;


public interface AccountService {

     ResponseEntity<AccountResponse> getAccountById(Long accId);

     List<AccountResponse> getAllAccounts();

     ResponseEntity<AccountResponse> createAccount(UUID userId, AccountRequest account);

     ResponseEntity<AccountResponse> updateAccount(Long accountId, AccountRequest registerRequest) throws AccountNotFoundException;

     void deleteAccount(Long id) throws AccountNotFoundException;

     Float changeBalance(String source, String destination, Float amount) throws AccountNotFoundException;

     ResponseEntity<AccountResponseForMsTransfer> getByNumber(String number) throws AccountNotFoundException;
}

