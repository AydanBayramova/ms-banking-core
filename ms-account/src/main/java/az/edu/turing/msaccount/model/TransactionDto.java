package az.edu.turing.msaccount.model;

import java.util.Date;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.resource.transaction.spi.TransactionStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private long id;

    @NotBlank(message = "Transaction source cannot be empty")
    private String source;

    @NotBlank(message = "Transaction Destination cannot be empty")
    private String destination;

    @NotBlank(message = "Transaction type cannot be empty")
    private String type;

    @NotBlank(message = "Transaction timestamp cannot be empty")
    private Date timeStamp;

    @NotBlank(message = "Transaction status cannot be empty")
    private TransactionStatus status;

}