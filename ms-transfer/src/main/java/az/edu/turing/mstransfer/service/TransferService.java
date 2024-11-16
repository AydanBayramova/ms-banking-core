package az.edu.turing.mstransfer.service;

import az.edu.turing.mstransfer.model.dto.TransferDto;

import javax.security.auth.login.AccountNotFoundException;


public interface TransferService {

    String accountToAccountTransfer(TransferDto transferRequest) throws AccountNotFoundException;


}
