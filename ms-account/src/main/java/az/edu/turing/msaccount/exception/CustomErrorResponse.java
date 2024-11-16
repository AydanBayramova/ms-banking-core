package az.edu.turing.msaccount.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomErrorResponse {
    private int status;
    private String title;
    private String detail;
    private String instance;
    private LocalDateTime timestamp;

}
