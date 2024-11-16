package az.edu.turing.mstransfer.service;

import az.edu.turing.mstransfer.model.dto.TransferDto;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;


public interface TransferService {

    String accountToAccountTransfer(String userId, TransferDto transferRequest);

    ResponseEntity<List<TransferDto>> getTranfersByUserId(Long userId);
}
