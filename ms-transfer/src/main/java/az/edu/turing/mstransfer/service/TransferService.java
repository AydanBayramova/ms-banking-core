package az.edu.turing.mstransfer.service;

import az.edu.turing.mstransfer.model.dto.TransferRequest;
import az.edu.turing.mstransfer.model.dto.TransferResponse;

import javax.security.auth.login.AccountNotFoundException;


public interface TransferService {

    TransferResponse accountToAccountTransfer(TransferRequest transferRequest) throws AccountNotFoundException;


}
