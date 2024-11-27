package az.edu.turing.msidentity.service.impl;

import az.edu.turing.msidentity.model.ChangePassword;
import az.edu.turing.msidentity.entity.ForgotPassword;
import az.edu.turing.msidentity.model.MailBody;
import az.edu.turing.msidentity.entity.UserEntity;
import az.edu.turing.msidentity.repository.ForgotPasswordRepository;
import az.edu.turing.msidentity.repository.UserRepository;
import az.edu.turing.msidentity.repository.UserRepositoryCustom;
import az.edu.turing.msidentity.service.inter.EmailService;
import az.edu.turing.msidentity.service.inter.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepositoryCustom userRepositoryCustom;

    @Override
    public String verifyMail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Provide valid email"));

        int otp = otpGenerator();

        MailBody mailBody = MailBody.builder()
                .to(email)
                .body("This is the OTP code : " + otp)
                .subject("OTP for Forgot Password request")
                .build();

        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 60 *5 * 1000))
                .userId(user.getId())
                .build();

        emailService.sendSimpleMail(mailBody);
        forgotPasswordRepository.save(fp);

        return "OTP sent to email.";
    }

    @Override
    public String verifyOtp(Integer otp, String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Provide valid email"));

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUserId(otp, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provide valid OTP"));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.delete(fp);
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "OTP has expired");
        }

        return "OTP verified.";
    }

    @Override
    public String changePassword(ChangePassword changePassword, String email) {

        if (!Objects.equals(changePassword.password(), changePassword.repeatedPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");

        }

        String encodedPassword = passwordEncoder.encode(changePassword.password());
        userRepositoryCustom.updatePasswordByEmail(email, encodedPassword);

        return "Password changed successfully.";
    }

    @Override
    @Scheduled(fixedRate = 300000)
    public void cleanExpiredOtps() {
        forgotPasswordRepository.deleteExpiredOtps();
    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }


}
