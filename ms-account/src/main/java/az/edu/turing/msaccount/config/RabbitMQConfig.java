package az.edu.turing.msaccount.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // ms-account üçün əsas Queue, Exchange, və Routing Key
    @Value("${account.queue}")
    private String accountQueue;

    @Value("${account.exchange}")
    private String accountExchange;

    @Value("${account.routingKey}")
    private String accountRoutingKey;

    // ms-notification üçün əlavə Queue və Routing Key
    @Value("${notification.queue}")
    private String notificationQueue;

    @Value("${notification.routingKey}")
    private String notificationRoutingKey;

    @Bean
    public Queue accountQueue() {
        return new Queue(accountQueue, true);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(notificationQueue, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(accountExchange);
    }

    @Bean
    public Binding accountBinding(Queue accountQueue, TopicExchange exchange) {
        return BindingBuilder.bind(accountQueue).to(exchange).with(accountRoutingKey);
    }

    @Bean
    public Binding notificationBinding(Queue notificationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(notificationQueue).to(exchange).with(notificationRoutingKey);
    }
}
