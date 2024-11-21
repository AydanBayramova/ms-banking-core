package az.edu.turing.msaccount.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-transfer", path = "/api/v1/transfers")
public interface TransferFeignService {

    @GetMapping("account/{accNumber}")
    ResponseEntity<List<TransferDto>> getTransfersByAccNumber(@PathVariable("accNumber") String accNumber);
}
