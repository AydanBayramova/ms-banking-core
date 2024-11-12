package az.edu.turing.mstransfer.service;

import az.edu.turing.mstransfer.model.dto.TransferRequest;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;


public interface TransferService {

    void accountToAccountTransfer(TransferRequest transferRequest) throws AccountNotFoundException;


}
