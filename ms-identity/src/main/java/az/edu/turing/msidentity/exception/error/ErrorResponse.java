package az.edu.turing.msidentity.exception.error;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private HttpStatus status;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime timestamp;
    private String message;
    private String path;
}
