package az.edu.turing.msidentity.service.inter;



import az.edu.turing.msidentity.model.ChangePassword;

public interface ForgotPasswordService {
    String verifyMail(String email);
    String verifyOtp(Integer otp, String email);
    String changePassword(ChangePassword changePassword, String email);
}
