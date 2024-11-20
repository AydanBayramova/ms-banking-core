package az.edu.turing.msaccount.service.impl;

import az.edu.turing.msaccount.entity.Account;
import az.edu.turing.msaccount.enums.AccountStatus;
import az.edu.turing.msaccount.exception.AccountNotFoundException;
import az.edu.turing.msaccount.exception.CanNotBeBlankException;
import az.edu.turing.msaccount.mapper.AccountMapper;
import az.edu.turing.msaccount.model.TransactionDto;
import az.edu.turing.msaccount.model.request.AccountRequest;
import az.edu.turing.msaccount.model.response.AccountResponse;
import az.edu.turing.msaccount.model.response.AccountResponseForMsTransfer;
import az.edu.turing.msaccount.repository.AccountRepository;
import az.edu.turing.msaccount.service.AccountService;
import az.edu.turing.msaccount.service.TransferFeign;
import az.edu.turing.msaccount.util.Generator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final AccountMapper accountMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TransferFeign feign;


    @Override
    public ResponseEntity<AccountResponse> getAccountById(Long accId) {
        Optional<Account> accountEntity = repository.findById(accId);

        if (accountEntity.get().getActive() == AccountStatus.ACTIVATED) {
            return ResponseEntity.ok(accountMapper.toAccountDto(accountEntity.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        return accountMapper.listToAccountDto(repository.findAll());
    }

    @Override
    public ResponseEntity<AccountResponse> createAccount(UUID userId, AccountRequest account) {


        Account accountEntity = accountMapper.toAccountEntity(account);
        accountEntity.setNumber(Generator.randomNumberGenerator());
        accountEntity.setActive(AccountStatus.ACTIVATED);
        accountEntity.setUserId(userId);
        accountEntity.setBalance(2000F);
        accountEntity.setPassword(passwordEncoder.encode(account.getPassword()));
        accountEntity.setOpeningDate(LocalDateTime.now());
        accountEntity.setAccountId((101 + repository.count()));

        Account savedAccount = repository.save(accountEntity);

        return ResponseEntity.ok(accountMapper.toAccountDto(savedAccount));
    }


    @Override
    public ResponseEntity<AccountResponse> updateAccount(Long accountId, AccountRequest registerRequest) throws AccountNotFoundException {
        Account accountEntity = repository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        accountMapper.updateEntityFromRequest(registerRequest, accountEntity);

        accountEntity.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        //byte[] profilePhotoBytes = Base64.getDecoder().decode(registerRequest.getProfilePhoto());
        //accountEntity.setProfilePhoto(profilePhotoBytes);

        Account updatedAccount = repository.save(accountEntity);
        AccountResponse response = accountMapper.toAccountDto(updatedAccount);
        return ResponseEntity.ok(response);
    }

    @Override
    public void deleteAccount(Long id) throws AccountNotFoundException {
        Account accountEntity = repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        accountEntity.setActive(AccountStatus.DEACTIVATED);
        repository.save(accountEntity);
    }

    @Override
    public Float changeBalance(String source, String destination, Float amount) throws AccountNotFoundException {
        Account sourceAccount = repository.findByNumber(source)
                .orElseThrow(() -> new AccountNotFoundException("Source Account not found"));

        if (sourceAccount.getBalance() < amount || sourceAccount.getActive() != AccountStatus.ACTIVATED) {
            return 0F;
        }

        Account destinationAccount = repository.findByNumber(destination)
                .orElseThrow(() -> new AccountNotFoundException("Destination Account not found"));

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        repository.save(sourceAccount);
        repository.save(destinationAccount);

        return amount;
    }

    @Override
    public ResponseEntity<AccountResponseForMsTransfer> getByNumber(String number) {
        if (number.isBlank()) {
            throw new CanNotBeBlankException("Number is blank");
        }
        Account account = repository.findByNumber(number).get();
        return ResponseEntity.ok(AccountResponseForMsTransfer.builder()
                .accountNumber(account.getNumber())
                .currency(account.getCurrency().name())
                .balance(BigDecimal.valueOf(account.getBalance())).build());
    }

    public ResponseEntity<AccountResponse> fetchAccountWithTransactions(Long accountId) {
        log.info("Fetching Account details with account id: {}", accountId);

        ResponseEntity<AccountResponse> account = getAccountById(accountId);

        if (account.getBody() == null || !account.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to fetch account details for id: {}", accountId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        log.info("Fetching transactions of account: {}", account.getBody().getNumber());
        ResponseEntity<List<TransactionDto>> transactions = feign.getTransactions(account.getBody().getNumber());

        if (transactions.getBody() == null || !transactions.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to fetch transactions for account number: {}", account.getBody().getNumber());

        } else {
            log.info("Received transactions response: {}", transactions.getBody());
        }

        return account;
    }


}
