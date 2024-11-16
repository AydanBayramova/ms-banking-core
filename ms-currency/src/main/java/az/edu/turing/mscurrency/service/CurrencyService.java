package az.edu.turing.mscurrency.service;

import az.edu.turing.mscurrency.client.ThirdPartyCurrencyClient;
import az.edu.turing.mscurrency.model.CurrencyRateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.Map;

@Service
@RequiredArgsConstructor

public class CurrencyService {

    private final ThirdPartyCurrencyClient currencyClient;
    private final CacheManager cacheManager;

    @Value("${exchange.api.key}")
    private String apiKey;

    @Cacheable(value = "currencyRates", key = "#base + '/' + #target")
    public CurrencyRateDto getCurrencyRate(String base, String target) {
        System.out.println("API called: " + base + " -> " + target);
        Map<String, Object> response = currencyClient.getCurrencyRate(base, target, apiKey);

        if (response == null || !(Boolean) response.get("success")) {
            throw new RuntimeException("Currency rate not found");
        }

        Map<String, Double> rates = (Map<String, Double>) response.get("rates");
        double rate = rates != null ? rates.get(target) : 0.0;

        return new CurrencyRateDto(base, target, rate);
    }
}
