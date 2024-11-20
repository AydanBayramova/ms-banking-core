package az.edu.turing.mstransfer.domain.repository;

import az.edu.turing.mstransfer.domain.entity.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, Long> {

    List<TransferEntity> getTransferEntitiesByUserId(String userId);

    Optional<List<TransferEntity>> getTransferEntityBySourceAccount(String sourceAccountNumber);
}
