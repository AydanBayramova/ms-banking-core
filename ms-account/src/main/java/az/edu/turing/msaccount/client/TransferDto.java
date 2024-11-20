package az.edu.turing.msaccount.client;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class TransferDto {

    @NotEmpty(message = "This field cannot be empty")
    private String from;

    @NotEmpty(message = "This field cannot be empty")
    private String to;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
}
