package az.edu.turing.msaccount.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class NotificationEvent {

    private String eventType;
    private String email;
    private String message;
}
