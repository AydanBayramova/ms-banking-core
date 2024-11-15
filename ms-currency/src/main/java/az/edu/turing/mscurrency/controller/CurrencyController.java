package az.edu.turing.mscurrency.controller;

import az.edu.turing.mscurrency.model.CurrencyRateDto;
import az.edu.turing.mscurrency.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/rate")
    public ResponseEntity<CurrencyRateDto> getCurrencyRate(@RequestParam String base, @RequestParam String target) {
        CurrencyRateDto rate = currencyService.getCurrencyRate(base, target);
        return ResponseEntity.ok(rate);
    }
}
