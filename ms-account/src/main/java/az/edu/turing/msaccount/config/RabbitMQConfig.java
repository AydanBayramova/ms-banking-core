package az.edu.turing.msaccount.config;

import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RabbitMQConfig {

    @Value("${account.queue}")
    private String queueName;

    @Value("${account.exchange}")
    private String exchangeName;

    @Value("${account.routingKey}")
    private String routingKey;

    @Bean
    public Queue queue() {
        return new Queue(queueName, true); // Durable = true
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
}
