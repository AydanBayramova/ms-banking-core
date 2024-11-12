package az.edu.turing.mstransfer.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse {

    @NotNull(message = "From Account ID cannot be null")
    @Positive(message = "From Account ID must be positive")
    private Long fromAccountId;

    @NotNull(message = "To Account ID cannot be null")
    @Positive(message = "To Account ID must be positive")
    private Long toAccountId;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;


    private String message;

}
