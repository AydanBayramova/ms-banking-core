package az.edu.turing.mstransfer.exception;

import az.edu.turing.mstransfer.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(
            AccountNotFoundException ex, WebRequest request) {
        return createErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(CurrencyRateNotFounException.class)
    public ResponseEntity<ErrorResponse> handleCurrencyRateNotFoundException(
            CurrencyRateNotFounException ex, WebRequest request) {
        return createErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(IdCannotBeNullException.class)
    public ResponseEntity<ErrorResponse> handleIdCannotBeNullException(
            IdCannotBeNullException ex, WebRequest request) {
        return createErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(
            InsufficientBalanceException ex, WebRequest request) {
        return createErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(WebRequest request) {
        return createErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                request.getDescription(false)
        );
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(
            HttpStatus status, String message, String path) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .build();

        return new ResponseEntity<>(errorResponse, status);
    }
}
