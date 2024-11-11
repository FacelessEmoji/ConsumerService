package rut.miit.consumerservice.services.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.consumerservice.models.entities.Order;
import rut.miit.consumerservice.models.entities.Part;
import rut.miit.consumerservice.repositories.OrderRepository;
import rut.miit.consumerservice.repositories.PartRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Service
public class PartAvailabilityService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void checkAndUpdatePartsAvailability(String orderId) {
        System.out.println("Starting part availability check for Order ID: " + orderId);

        Order order = getOrderById(orderId);
        if (order == null) {
            return;
        }

        String requiredParts = order.getRequiredParts();
        if (requiredParts == null || requiredParts.isEmpty()) {
            System.out.println("Order ID " + orderId + " has no parts required.");
            return;
        }

        checkPartsAvailability(orderId, requiredParts);
    }

    private Order getOrderById(String orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            System.out.println("Order not found with ID: " + orderId);
        }
        return order;
    }

    private void checkPartsAvailability(String orderId, String requiredParts) {
        String[] partsArray = requiredParts.split(";");
        boolean allPartsAvailable = true;
        StringBuilder missingParts = new StringBuilder();

        for (String partName : partsArray) {
            partName = partName.trim();
            Part part = partRepository.findByName(partName);

            System.out.println("Checking availability for part: " + partName);

            if (part == null || part.getQuantity() <= 0) {
                allPartsAvailable = false;
                missingParts.append(partName).append("; ");
                System.out.println("Part " + partName + " is missing or out of stock.");
            } else {
                updatePartQuantity(part);
            }
        }

        if (allPartsAvailable) {
            System.out.println("All parts are available for Order ID: " + orderId);
        } else {
            String missingPartsList = missingParts.toString();
            System.out.println("Missing parts for Order ID " + orderId + ": " + missingPartsList);
            sendWaitingForPartsMessage(orderId, missingPartsList);
        }
    }

    private void updatePartQuantity(Part part) {
        part.setQuantity(part.getQuantity() - 1);
        partRepository.save(part);
        System.out.println("Updated quantity for part: " + part.getName());
    }

    private void sendWaitingForPartsMessage(String orderId, String missingPartsList) {
        String message = "Order " + orderId + " is waiting for parts: " + missingPartsList;
        System.out.println("Sending message to exchange: " + message);
        rabbitTemplate.convertAndSend(
                "spring-boot-exchange",
                "order.waitingForParts",
                message
        );
    }
}
