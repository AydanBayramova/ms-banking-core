//package az.edu.turing.msaccount.client;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class FeignService {
//    private final CurrencyClient currencyClient;
//
//    public double convertBalance(double balance, String fromCurrency, String toCurrency) {
//        CurrencyResponse rate = currencyClient.getExchangeRate(fromCurrency, toCurrency);
//        return balance * rate.getRate();
//    }
//}
