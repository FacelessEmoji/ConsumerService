package rut.miit.consumerservice.services.interfaces;

import rut.miit.consumerservice.dtos.main.OrderPartDTO;
import rut.miit.consumerservice.models.entities.OrderPart;

import java.util.List;

public interface OrderPartService<ID> {
    List<OrderPartDTO> getAllOrderParts();
    OrderPart getOrderPartById(ID id);
    OrderPartDTO createOrderPart(OrderPartDTO orderPartDTO);
    OrderPartDTO updateOrderPart(ID id, OrderPartDTO orderPartDTO);
    void deleteOrderPart(ID id);

    OrderPartDTO updateOrderPartQuantity(ID id, Integer quantity);
}

