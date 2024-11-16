package az.edu.turing.mstransfer.service.impl;

import az.edu.turing.mstransfer.domain.entity.AccountEntity;
import az.edu.turing.mstransfer.domain.repository.AccountRepository;
import az.edu.turing.mstransfer.exception.InvalidTransferException;
import az.edu.turing.mstransfer.exception.TransferValidationException;
import az.edu.turing.mstransfer.model.dto.TransferRequest;
import az.edu.turing.mstransfer.model.dto.TransferResponse;
import az.edu.turing.mstransfer.model.mapper.TransferMapper;
import az.edu.turing.mstransfer.service.TransferService;
import az.edu.turing.mstransfer.model.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;
    private final TransferMapper transferMapper;

    @Override
    public TransferResponse accountToAccountTransfer(TransferRequest request) throws AccountNotFoundException {

        AccountEntity sender = accountRepository.findByAccountIdAndStatus(String.valueOf(request.getFromAccountId()), Status.ACTIVE)
                .orElseThrow(() -> new AccountNotFoundException("No such Active account"));
        checkAmountAndBalance(sender.getBalance(), request.getAmount());

        AccountEntity receiver = accountRepository.findByAccountIdAndStatus(String.valueOf(request.getToAccountId()), Status.ACTIVE)
                .orElseThrow(() -> new AccountNotFoundException("No such Active account"));
        validateReceiverAccount(transferMapper.accountEntityToResponse(sender), transferMapper.accountEntityToResponse(receiver));

        sender.setBalance(sender.getBalance().subtract(request.getAmount()));
        accountRepository.save(sender);

        receiver.setBalance(receiver.getBalance().add(request.getAmount()));
        accountRepository.save(receiver);

        return null;
    }

    private void checkAmountAndBalance(BigDecimal balance, BigDecimal amountToTransfer) {
        if (amountToTransfer == null || amountToTransfer.compareTo(BigDecimal.ONE) < 0) {
            throw new TransferValidationException("Invalid amount! The amount must be greater than or equal to 1.");
        }
        if (balance == null || balance.compareTo(amountToTransfer) < 0) {
            throw new TransferValidationException("Insufficient balance!");
        }
    }


    private void validateReceiverAccount(TransferResponse sender, TransferResponse receiver) {
        if (sender.getFromAccountId().equals(receiver.getToAccountId())) {
            throw new InvalidTransferException("Sender and receiver can not be the same");
        }
    }

}
