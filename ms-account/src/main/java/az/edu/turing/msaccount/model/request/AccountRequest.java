package az.edu.turing.msaccount.model.request;

import az.edu.turing.msaccount.enums.AccountType;
import az.edu.turing.msaccount.enums.BankType;
import az.edu.turing.msaccount.enums.CurrencyType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {

  //  @NotBlank(message = "Username is mandatory")
    private String username;

  //  @NotBlank(message = "Password is mandatory")
    private String password;

   // @NotBlank(message = "Email is mandatory")
    private String email;

    private BankType bank;
    private AccountType type;
    private CurrencyType currency;

}
