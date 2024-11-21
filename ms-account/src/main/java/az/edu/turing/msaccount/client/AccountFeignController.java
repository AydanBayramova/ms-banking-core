//package az.edu.turing.msaccount.client;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/account")
//@RequiredArgsConstructor
//public class AccountFeignController {
//     private final FeignService accountService;
//
//
//    @GetMapping("/convert-balance")
//    public ResponseEntity<Double> convertBalance(
//            @RequestParam double balance,
//            @RequestParam String from,
//            @RequestParam String to) {
//        double converted = accountService.convertBalance(balance, from, to);
//        return ResponseEntity.ok(converted);
//    }
//}
