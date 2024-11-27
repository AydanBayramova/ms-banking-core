package az.edu.turing.msnotification.component;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class NotificationEvent {
    private String eventType;
    private String email;
    private String message;
}
