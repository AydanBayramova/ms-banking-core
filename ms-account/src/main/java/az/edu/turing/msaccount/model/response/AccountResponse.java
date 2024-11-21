package az.edu.turing.msaccount.model.response;

import az.edu.turing.msaccount.client.TransferDto;
import az.edu.turing.msaccount.enums.AccountType;
import az.edu.turing.msaccount.enums.BankType;
import az.edu.turing.msaccount.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {

    private String username;

    private String message;

    private String email;

    private Float balance;

    private List<TransferDto> transfers;

    private BankType bank;

    private AccountType type;

    private CurrencyType currency;

    private String number;
}
