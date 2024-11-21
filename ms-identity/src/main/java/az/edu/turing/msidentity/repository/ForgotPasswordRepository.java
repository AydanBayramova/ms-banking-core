package az.edu.turing.msidentity.repository;

import az.edu.turing.msidentity.entity.ForgotPassword;
import az.edu.turing.msidentity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {

    Optional<ForgotPassword> findByOtpAndUserId(Integer otp, UUID userId);
}
