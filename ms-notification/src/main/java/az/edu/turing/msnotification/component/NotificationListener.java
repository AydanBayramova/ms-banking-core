package az.edu.turing.msnotification.component;

import az.edu.turing.msnotification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final EmailService emailService;

    @RabbitListener(queues = "notification-queue")
    public void handleNotification(NotificationEvent event) {
        emailService.sendEmail(event.getEmail(), "Notification: " + event.getEventType(), event.getMessage());
        System.out.println("Email sent to: " + event.getEmail());
    }
}

