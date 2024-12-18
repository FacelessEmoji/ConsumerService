package rut.miit.consumerservice.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.consumerservice.dtos.main.OrderPartDTO;
import rut.miit.consumerservice.models.entities.OrderPart;
import rut.miit.consumerservice.repositories.OrderPartRepository;
import rut.miit.consumerservice.repositories.OrderRepository;
import rut.miit.consumerservice.repositories.PartRepository;
import rut.miit.consumerservice.services.interfaces.OrderPartService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderPartServiceImpl implements OrderPartService<String> {
    private OrderPartRepository orderPartRepository;
    private OrderRepository orderRepository;
    private PartRepository partRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setOrderPartRepository(OrderPartRepository orderPartRepository) {
        this.orderPartRepository = orderPartRepository;
    }
    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Autowired
    public void setPartRepository(PartRepository partRepository) {
        this.partRepository = partRepository;
    }
    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<OrderPartDTO> getAllOrderParts() {
        return orderPartRepository.findAll().stream()
                .map(op -> modelMapper.map(op, OrderPartDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderPart getOrderPartById(String s) {
        return orderPartRepository.findById(s).orElse(null);
    }

    @Override
    public OrderPartDTO createOrderPart(OrderPartDTO orderPartDTO) {
        OrderPart orderPart = modelMapper.map(orderPartDTO, OrderPart.class);
        orderPart.setOrder(orderRepository.findById(orderPartDTO.getOrder()).orElse(null));
        orderPart.setPart(partRepository.findById(orderPartDTO.getPart()).orElse(null));
        return modelMapper.map(orderPartRepository.
                saveAndFlush(modelMapper.map(orderPart, OrderPart.class)), OrderPartDTO.class);
    }

    @Override
    public OrderPartDTO updateOrderPart(String s, OrderPartDTO orderPartDTO) {
        OrderPart orderPart = orderPartRepository.findById(s).orElseThrow();
        orderPart.setQuantity(orderPartDTO.getQuantity());
        orderPart.setPart(partRepository.findById(orderPartDTO.getPart()).orElseThrow());
        orderPart.setOrder(orderRepository.findById(orderPartDTO.getOrder()).orElseThrow());
        return modelMapper.map(orderPartRepository.saveAndFlush(orderPart), OrderPartDTO.class);
    }

    @Override
    public void deleteOrderPart(String s) {
        orderPartRepository.deleteById(s);
    }

    @Override
    public OrderPartDTO updateOrderPartQuantity(String s, Integer quantity) {
        OrderPart orderPart = orderPartRepository.findById(s).orElseThrow();
        orderPart.setQuantity(quantity);
        return modelMapper.map(orderPartRepository.saveAndFlush(orderPart), OrderPartDTO.class);
    }
}

