package rut.miit.consumerservice.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import rut.miit.consumerservice.config.ApplicationBeanConfiguration;
import rut.miit.consumerservice.services.rabbitmq.OrderCalculationService;
import rut.miit.consumerservice.services.rabbitmq.PartAvailabilityService;

@Component
public class Receiver {

    @Autowired
    private OrderCalculationService orderCalculationService;

    @Autowired
    private PartAvailabilityService partAvailabilityService;

    // Слушатель для обработки наличия запчастей
    @RabbitListener(queues = "order-parts-queue", concurrency = "3-5")  // Параллельная обработка (от 3 до 5 потоков)
    public void receiveOrderParts(String orderId) {
        System.out.println("ConsumerService received Order ID: <" + orderId + "> for part availability check");
        partAvailabilityService.checkAndUpdatePartsAvailability(orderId);
    }

    // Слушатель для расчета цены заказа
    @RabbitListener(queues = "order-price-queue", concurrency = "3-5")  // Параллельная обработка (от 3 до 5 потоков)
    public void receiveOrderPrice(String orderId) {
        System.out.println("ConsumerService received Order ID: <" + orderId + "> for price calculation");
        orderCalculationService.calculateAndUpdateEstimatedTime(orderId);
    }
}
