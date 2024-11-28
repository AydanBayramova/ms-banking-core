package az.edu.turing.msidentity.controller;

import az.edu.turing.msidentity.model.ChangePassword;
import az.edu.turing.msidentity.service.inter.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forgotPassword")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/verifyMail/{email}")
    public ResponseEntity<String> verifyMail(@PathVariable String email) {
        String response = forgotPasswordService.verifyMail(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        String response = forgotPasswordService.verifyOtp(otp, email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword changePassword,
                                                 @RequestParam Integer otp,
                                                 @PathVariable String email) {
        forgotPasswordService.verifyOtp(otp, email);
        String response = forgotPasswordService.changePassword(changePassword, email);
        return ResponseEntity.ok(response);
    }

}
