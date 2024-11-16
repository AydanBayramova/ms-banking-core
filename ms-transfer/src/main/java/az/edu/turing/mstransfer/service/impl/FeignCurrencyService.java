package az.edu.turing.mstransfer.service.impl;

import az.edu.turing.mstransfer.model.dto.CurrencyRateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "currency",url = "http://localhost:8087")
public interface FeignCurrencyService {

    @GetMapping("/api/currency/rate")
    ResponseEntity<CurrencyRateDto> getCurrencyRate(@RequestParam String base,@RequestParam String target);
}
