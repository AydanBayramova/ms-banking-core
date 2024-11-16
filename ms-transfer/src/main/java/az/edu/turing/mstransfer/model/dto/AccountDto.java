package az.edu.turing.mstransfer.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class AccountDto {

    private String accountNumber;
    private String currency;
    private BigDecimal balance;
}
