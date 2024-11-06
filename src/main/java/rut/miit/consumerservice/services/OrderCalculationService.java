package rut.miit.consumerservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.consumerservice.models.entities.Order;
import rut.miit.consumerservice.repositories.OrderRepository;

import java.time.LocalDateTime;

@Service
public class OrderCalculationService {

    @Autowired
    private OrderRepository orderRepository;

    public void calculateAndUpdateEstimatedTime(String orderId) {
        // Получаем заказ по ID
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            System.out.println("Order not found with ID: " + orderId);
            return;
        }

        // Допустим, что стандартное время выполнения — 2 дня
        LocalDateTime estimatedCompletionTime = LocalDateTime.now().plusDays(2);

        // Обновляем время выполнения в заказе
        order.setEstimatedCompletionTime(estimatedCompletionTime);
        orderRepository.save(order);

        System.out.println("Updated estimated completion time for Order ID: " + orderId);
    }
}
