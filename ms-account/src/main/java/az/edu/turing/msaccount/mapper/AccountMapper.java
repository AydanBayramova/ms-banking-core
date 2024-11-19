package az.edu.turing.msaccount.mapper;

import az.edu.turing.msaccount.entity.Account;
import az.edu.turing.msaccount.model.request.AccountRequest;
import az.edu.turing.msaccount.model.response.AccountResponse;
import org.mapstruct.*;

import java.util.List;


@Mapper (componentModel = "spring")
public interface AccountMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(AccountRequest registerRequest, @MappingTarget Account accountEntity);


    Account toAccountEntity(AccountRequest accountRequest);

    AccountResponse toAccountDto(Account accountEntity);

    List<Account> listToAccountEntity(List<AccountRequest> accountRequests);

    List<AccountResponse> listToAccountDto(List<Account> accountEntity);
}