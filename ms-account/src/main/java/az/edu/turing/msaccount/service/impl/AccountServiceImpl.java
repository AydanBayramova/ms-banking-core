package az.edu.turing.msaccount.service.impl;

import az.edu.turing.msaccount.client.TransferDto;
import az.edu.turing.msaccount.client.TransferFeignService;
import az.edu.turing.msaccount.entity.Account;
import az.edu.turing.msaccount.enums.AccountStatus;
import az.edu.turing.msaccount.exception.AccountNotFoundException;
import az.edu.turing.msaccount.exception.CanNotBeBlankException;
import az.edu.turing.msaccount.exception.TransactionFetchException;
import az.edu.turing.msaccount.mapper.AccountMapper;
import az.edu.turing.msaccount.model.request.AccountRequest;
import az.edu.turing.msaccount.model.response.AccountResponse;
import az.edu.turing.msaccount.model.response.AccountResponseForMsTransfer;
import az.edu.turing.msaccount.repository.AccountRepository;
import az.edu.turing.msaccount.service.AccountService;
import az.edu.turing.msaccount.util.Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final AccountMapper accountMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TransferFeignService feignClient;


    public AccountResponse fetchAccountDetailsWithTransactions(Long accountId) {
        Optional<Account> accountEntity = repository.findById(accountId);

        if (accountEntity.isEmpty() || accountEntity.get().getActive() != AccountStatus.ACTIVATED) {
            throw new AccountNotFoundException("Account not found or not active: " + accountId);
        }

        AccountResponse accountResponse = accountMapper.toAccountDto(accountEntity.get());

        ResponseEntity<List<TransferDto>> transactionsResponse = feignClient.getTransfersByAccNumber(accountResponse.getNumber());

        if (transactionsResponse.getBody() == null || !transactionsResponse.getStatusCode().is2xxSuccessful()) {
            throw new TransactionFetchException("Failed to fetch transactions", accountResponse);
        }

        accountResponse.setTransfers(transactionsResponse.getBody());
        return accountResponse;
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
        accountEntity.setCurrency(account.getCurrency());

        Account savedAccount = repository.save(accountEntity);

        return ResponseEntity.ok(accountMapper.toAccountDto(savedAccount));
    }


    @Override
    public ResponseEntity<AccountResponse> updateAccount(Long accountId, AccountRequest registerRequest) throws AccountNotFoundException {
        Account accountEntity = repository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        accountMapper.updateEntityFromRequest(registerRequest, accountEntity);

        accountEntity.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

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
//    @Transactional
//    public float updateBalances(String fromAccount, String toAccount, float amount) {
//        Account from = repository.findByNumber(fromAccount)
//                .orElseThrow(() -> new AccountNotFoundException("From account not found"));
//
//        Account to = repository.findByNumber(toAccount)
//                .orElseThrow(() -> new AccountNotFoundException("To account not found"));
//
//        if (from.getBalance() < amount) {
//            throw new RuntimeException("Insufficient balance");
//        }
//
//        from.setBalance(from.getBalance() - amount);
//        to.setBalance(to.getBalance() + amount);
//
//        repository.save(from);
//        repository.save(to);
//
//        return from.getBalance();
//    }



}
