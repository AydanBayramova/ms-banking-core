package az.edu.turing.mstransfer.model.mapper;


import az.edu.turing.mstransfer.domain.entity.AccountEntity;
import az.edu.turing.mstransfer.model.dto.TransferRequest;
import az.edu.turing.mstransfer.model.dto.TransferResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransferMapper {

    AccountEntity transferRequestToEntity(TransferRequest transferRequest);
    TransferRequest accountEntityToRequest(AccountEntity accountEntity);
    AccountEntity transferResponseToEntity(TransferResponse transferResponse);
    TransferResponse accountEntityToResponse(AccountEntity accountEntity);


}
