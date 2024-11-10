package az.edu.turing.msidentity.exception;


import az.edu.turing.msidentity.model.enums.ResponseMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class BaseException extends RuntimeException {
    private final ResponseMessage responseMessage;

    public static BaseException of(ResponseMessage responseMessage) {
        return BaseException.builder()
                .responseMessage(responseMessage)
                .build();
    }

    @Override
    public String getMessage() {
        return responseMessage.getMessage();
    }
}
