package az.edu.turing.msidentity.model.enums;

import org.springframework.http.HttpStatus;

public enum ErrorMessages implements ResponseMessage {

    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "User already exists", HttpStatus.BAD_REQUEST),
    INVALID_USER_ID("INVALID_USER_ID", "Invalid user id", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("USER_NOT_FOUND", "User not found", HttpStatus.NOT_FOUND),
    USER_REQUEST_CANNOT_BE_NULL("USER_REQUEST_CANNOT_BE_NULL", "User request cannot be null", HttpStatus.BAD_REQUEST);

    private final String key;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorMessages(String key, String message, HttpStatus httpStatus) {
        this.key = key;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
