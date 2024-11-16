package az.edu.turing.msaccount.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AccountResponseForMsTransfer {

    private String accountNumber;
    private String currency;
    private BigDecimal balance;
}
