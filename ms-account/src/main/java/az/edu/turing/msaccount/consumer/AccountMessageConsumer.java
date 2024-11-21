package az.edu.turing.msaccount.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AccountMessageConsumer {

    @RabbitListener(queues = "${account.queue}")
    public void handleTransferMessage(String message) {
        System.out.println("Received transfer event: " + message);
    }
}
