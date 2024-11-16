package az.edu.turing.msaccount.repository;

import az.edu.turing.msaccount.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByNumber(String accountNumber);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUsername(String username);

}
