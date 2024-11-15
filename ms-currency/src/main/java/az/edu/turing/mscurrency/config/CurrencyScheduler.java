package az.edu.turing.mscurrency.config;

import az.edu.turing.mscurrency.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyScheduler {

    private final CurrencyService currencyService;


    @Scheduled(fixedRate = 60000)
    public void updateCurrencyRates() {
        System.out.println("Currency rates updated...");
        currencyService.getCurrencyRate("EUR", "AZN");
        currencyService.getCurrencyRate("EUR", "USD");
    }
}
