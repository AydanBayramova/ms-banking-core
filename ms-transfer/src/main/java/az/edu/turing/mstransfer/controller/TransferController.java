package az.edu.turing.mstransfer.controller;


import az.edu.turing.mstransfer.model.dto.TransferDto;
import az.edu.turing.mstransfer.service.impl.TransferServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfers")
public class TransferController {

    private final TransferServiceImpl transferServiceImpl;

    @PostMapping("/{userId}")
    public ResponseEntity<String> transfer(@PathVariable String userId, @Valid @RequestBody TransferDto transferRequest) {
        String amount = transferServiceImpl.accountToAccountTransfer(userId, transferRequest);
        return ResponseEntity.ok(amount);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<TransferDto>> getTransfers(@PathVariable String userId) {
        return transferServiceImpl.getTranfersByUserId(userId);
    }

    @GetMapping("account/{accNumber}")
    public ResponseEntity<List<TransferDto>> getTransfersByAccNumber(@PathVariable String accNumber) {
        return transferServiceImpl.getTransfersByAccNumber(accNumber);
    }
}
