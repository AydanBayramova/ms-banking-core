package az.edu.turing.mstransfer.model.mapper;


import az.edu.turing.mstransfer.domain.entity.AccountEntity;
import az.edu.turing.mstransfer.model.dto.TransferDto;
import az.edu.turing.mstransfer.model.dto.TransferResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransferMapper {

    AccountEntity transferRequestToEntity(TransferDto transferDto);

    TransferDto accountEntityToRequest(AccountEntity accountEntity);

}
