package az.edu.turing.msaccount.service;



import az.edu.turing.msaccount.model.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-transfer", url = "http://localhost:8090")
public interface TransferFeign {

    @GetMapping("/transaction/allBySource/{accountNumber}")
    ResponseEntity<List<TransactionDto>> getTransactions(@PathVariable("accountNumber") String accountNumber);

}
