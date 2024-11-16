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

    @Mappings({
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "bank", target = "bank"),
            @Mapping(source = "type", target = "type"),
            @Mapping(source = "currency", target = "currency")
    })
    Account toAccountEntity(AccountRequest accountRequest);

    AccountResponse toAccountDto(Account accountEntity);

    List<Account> listToAccountEntity(List<AccountRequest> accountRequests);

    List<AccountResponse> listToAccountDto(List<Account> accountEntity);
}