package az.edu.turing.mscurrency.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "currency-client", url = "${exchange.api.url}")
public interface ThirdPartyCurrencyClient {

    @GetMapping("/latest")
    Map<String, Object> getCurrencyRate(
            @RequestParam("base") String base,
            @RequestParam("symbols") String symbols,
            @RequestParam("access_key") String accessKey
    );
}
