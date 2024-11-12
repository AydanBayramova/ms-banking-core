package az.edu.turing.mstransfer.domain.repository;

import az.edu.turing.mstransfer.domain.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Long> {

}
