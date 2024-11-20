package az.edu.turing.msaccount.client;

import org.springframework.stereotype.Service;

@Service
public class FeignService {
    private CurrencyClient currencyClient;

    public double convertBalance(double balance, String fromCurrency, String toCurrency) {
        // Valyuta kurslarını əldə et
        CurrencyResponse rate = currencyClient.getExchangeRate(fromCurrency, toCurrency);
        // Balansı yeni valyutaya çevir
        return balance * rate.getRate();
    }
}
