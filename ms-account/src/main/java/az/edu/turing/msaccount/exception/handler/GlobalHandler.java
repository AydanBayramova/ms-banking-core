package az.edu.turing.msaccount.exception.handler;


import az.edu.turing.msaccount.exception.AccountNotFoundException;
import az.edu.turing.msaccount.exception.CanNotBeBlankException;
import az.edu.turing.msaccount.exception.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleAccountNotFoundException(
            AccountNotFoundException ex,
            WebRequest request) {

        CustomErrorResponse errorResponse = new CustomErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setTitle("Account Not Found");
        errorResponse.setDetail(ex.getMessage());
        errorResponse.setInstance(request.getDescription(false));
        errorResponse.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CanNotBeBlankException.class)
    public ResponseEntity<CustomErrorResponse> handleCanNotBeBlankException(
            CanNotBeBlankException ex,
            WebRequest request) {

        CustomErrorResponse errorResponse = new CustomErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTitle("Invalid Input");
        errorResponse.setDetail(ex.getMessage());
        errorResponse.setInstance(request.getDescription(false));
        errorResponse.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
