package rut.miit.consumerservice.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfiguration {

    public static final String QUEUE_NAME = "spring-boot"; // Имя должно совпадать с очередью в главном сервисе
    public static final String EXCHANGE_NAME = "spring-boot-exchange"; // Имя обмена

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false); // Non-durable queue
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME); // Создание обмена
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.baz"); // Binding с использованием routing key
    }
}

