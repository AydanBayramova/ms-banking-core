package az.edu.turing.msaccount.controller;

import az.edu.turing.msaccount.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class RabbitMQTestController {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;

    public RabbitMQTestController(RabbitTemplate rabbitTemplate, RabbitMQConfig rabbitMQConfig) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
    }

    @PostMapping("/send")
    public String sendTestMessage(@RequestBody String message) {
        rabbitTemplate.convertAndSend(rabbitMQConfig.getExchangeName(), rabbitMQConfig.getRoutingKey(), message);
        return "Message sent to RabbitMQ: " + message;
    }
}
