package az.edu.turing.msidentity.model.enums;

import org.springframework.http.HttpStatus;

public interface ResponseMessage {

    String getKey();

    String getMessage();

    HttpStatus getHttpStatus();
}
