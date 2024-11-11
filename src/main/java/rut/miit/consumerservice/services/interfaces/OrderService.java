package rut.miit.consumerservice.services.interfaces;

import rut.miit.consumerservice.dtos.main.OrderDTO;
import rut.miit.consumerservice.models.entities.Order;
import rut.miit.consumerservice.models.enums.StatusType;

import java.util.List;

public interface OrderService<ID> {
    List<OrderDTO> getOrdersByClientId(ID clientId);
    List<OrderDTO> getOrdersByMasterId(ID masterId);
    List<OrderDTO> getOrdersByClientIdAndMasterId(ID clientId, ID masterId);
    List<OrderDTO> getOrdersByStatus(StatusType status);

    List<OrderDTO> getAllOrders();
    Order getOrderById(ID id);
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(ID id, OrderDTO orderDTO);
    void deleteOrder(ID id);

    OrderDTO updateOrderStatus(ID id, StatusType status);
    OrderDTO assignMasterToOrder(ID orderId, ID masterId);
    OrderDTO assignClientToOrder(ID orderId, ID clientId);
    OrderDTO updateOrderDescription(ID orderId, ID description);
}

