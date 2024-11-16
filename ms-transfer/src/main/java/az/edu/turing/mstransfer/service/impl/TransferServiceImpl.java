package az.edu.turing.mstransfer.service.impl;

import az.edu.turing.mstransfer.domain.entity.TransferEntity;
import az.edu.turing.mstransfer.domain.repository.TransferRepository;
import az.edu.turing.mstransfer.exception.AccountNotFoundException;
import az.edu.turing.mstransfer.exception.CurrencyRateNotFounException;
import az.edu.turing.mstransfer.exception.IdCannotBeNullException;
import az.edu.turing.mstransfer.exception.InsufficientBalanceException;
import az.edu.turing.mstransfer.model.dto.AccountDto;
import az.edu.turing.mstransfer.model.dto.CurrencyRateDto;
import az.edu.turing.mstransfer.model.dto.TransferDto;
import az.edu.turing.mstransfer.service.TransferService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final FeignAccountService feignAccountService;
    private final FeignCurrencyService feignCurrencyService;
    private final TransferRepository transferRepository;


    @Override
    public String accountToAccountTransfer(String userId, TransferDto dto) {
        AccountDto fromAccount = getAccountOrThrow(dto.getFrom());
        AccountDto toAccount = getAccountOrThrow(dto.getTo());

        String fromCurrency = fromAccount.getCurrency();
        String toCurrency = toAccount.getCurrency();

        BigDecimal transferAmount = dto.getAmount();
        BigDecimal transferredAmount;

        if (fromCurrency.equals(toCurrency)) {
            validateBalance(fromAccount, transferAmount);
            transferredAmount = feignAccountService.changeBalance(
                    dto.getFrom(), dto.getTo(), transferAmount.floatValue()
            );
        } else {
            CurrencyRateDto currencyRate = getCurrencyRateOrThrow(fromCurrency, toCurrency);
            BigDecimal convertedAmount = transferAmount.multiply(BigDecimal.valueOf(currencyRate.getRate()));
            BigDecimal commissionFee = convertedAmount.multiply(BigDecimal.valueOf(currencyRate.getCommission()));
            BigDecimal totalAmount = convertedAmount.add(commissionFee);

            validateBalance(fromAccount, totalAmount);

            transferredAmount = feignAccountService.changeBalance(
                    dto.getFrom(), dto.getTo(), totalAmount.floatValue()
            );
        }

        createTransfer(TransferEntity.builder()
                .destinationAccount(dto.getFrom())
                .sourceAccount(dto.getTo())
                .amount(dto.getAmount())
                .userId(userId)
                .build());

        return "This amount of money transferred: " + transferredAmount;
    }

    @Override
    public ResponseEntity<List<TransferDto>> getTranfersByUserId(String userId) {
        if (userId == null) {
            throw new IdCannotBeNullException("Id cannot be null");
        }

        List<TransferDto> transferDtos = new ArrayList<>();

        for (TransferEntity transfer : transferRepository.getTransferEntitiesByUserId(userId)) {
            transferDtos.add(TransferDto.builder()
                    .from(transfer.getDestinationAccount())
                    .to(transfer.getSourceAccount())
                    .amount(transfer.getAmount()).build());

        }
        return ResponseEntity.ok(transferDtos);
    }

    private void createTransfer(TransferEntity transferEntity) {
        transferRepository.save(transferEntity);
    }


    private AccountDto getAccountOrThrow(String accountNumber) {
        AccountDto account = feignAccountService.getAccountByNumber(accountNumber).getBody();
        if (account == null) {
            throw new AccountNotFoundException("Account not found for number: " + accountNumber);
        }
        return account;
    }


    private void validateBalance(AccountDto account, BigDecimal requiredAmount) {
        if (account.getBalance().compareTo(requiredAmount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for account: " + account.getAccountNumber());
        }
    }


    private CurrencyRateDto getCurrencyRateOrThrow(String fromCurrency, String toCurrency) {
        CurrencyRateDto currencyRate = feignCurrencyService.getCurrencyRate(fromCurrency, toCurrency).getBody();
        if (currencyRate == null) {
            throw new CurrencyRateNotFounException("Currency rate not found for: " + fromCurrency + " to " + toCurrency);
        }
        return currencyRate;
    }


}
