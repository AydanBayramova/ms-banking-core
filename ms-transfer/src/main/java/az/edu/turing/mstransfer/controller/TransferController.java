package az.edu.turing.mstransfer.controller;


import az.edu.turing.mstransfer.model.dto.TransferDto;
import az.edu.turing.mstransfer.service.impl.TransferServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<String> transfer(@Valid @RequestBody TransferDto transferRequest) {
        String amount = transferServiceImpl.accountToAccountTransfer(transferRequest);
        return ResponseEntity.ok(amount);
    }


}
