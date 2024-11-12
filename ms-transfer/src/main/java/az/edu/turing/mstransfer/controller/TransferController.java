package az.edu.turing.mstransfer.controller;


import az.edu.turing.mstransfer.model.dto.TransferRequest;
import az.edu.turing.mstransfer.model.dto.TransferResponse;
import az.edu.turing.mstransfer.service.TransferService;
import az.edu.turing.mstransfer.service.impl.TransferServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfers")
public class TransferController {

    private final TransferServiceImpl transferServiceImpl;

    @PostMapping
    public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest transferRequest) throws AccountNotFoundException {
        TransferResponse transferResponse = transferServiceImpl.accountToAccountTransfer(transferRequest);
        return new ResponseEntity<>(transferResponse, HttpStatus.OK);
    }


}
