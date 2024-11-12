package az.edu.turing.mstransfer.domain.repository;

import az.edu.turing.mstransfer.domain.entity.AccountEntity;
import az.edu.turing.mstransfer.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByAccountIdAndStatus(String accountNumber, Status status);

}
