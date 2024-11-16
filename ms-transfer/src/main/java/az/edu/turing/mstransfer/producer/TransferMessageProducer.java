package az.edu.turing.mstransfer.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransferMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${transfer.exchange}")
    private String exchange;

    @Value("${transfer.routingKey}")
    private String routingKey;

    public TransferMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTransferEvent(String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        System.out.println("Message sent to RabbitMQ: " + message);
    }
}
