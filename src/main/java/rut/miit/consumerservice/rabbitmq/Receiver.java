package rut.miit.consumerservice.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import rut.miit.consumerservice.services.OrderCalculationService;

@Component
public class Receiver {

    @Autowired
    private OrderCalculationService orderCalculationService;

    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_NAME)
    public void receiveMessage(String orderId) {
        System.out.println("ConsumerService received Order ID: <" + orderId + "> for calculation");

        // Вызываем расчет времени выполнения заказа
        orderCalculationService.calculateAndUpdateEstimatedTime(orderId);
    }
}


