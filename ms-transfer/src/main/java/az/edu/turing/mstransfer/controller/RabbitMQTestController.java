package az.edu.turing.mstransfer.controller;

import az.edu.turing.mstransfer.producer.TransferMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class RabbitMQTestController {

    private final TransferMessageProducer producer;

    public RabbitMQTestController(TransferMessageProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam String message) {
        producer.sendTransferEvent(message);
        return ResponseEntity.ok("Message sent: " + message);
    }
}

