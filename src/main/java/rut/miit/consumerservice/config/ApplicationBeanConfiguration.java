package rut.miit.consumerservice.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rut.miit.consumerservice.dtos.main.OrderDTO;
import rut.miit.consumerservice.dtos.main.OrderPartDTO;
import rut.miit.consumerservice.models.entities.Order;
import rut.miit.consumerservice.models.entities.OrderPart;

@Configuration
@EnableRabbit
public class ApplicationBeanConfiguration {
    public static final String QUEUE_NAME = "order-queue";  // Имя очереди
    public static final String EXCHANGE_NAME = "spring-boot-exchange";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);  // Создание очереди
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("order.created");
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        TypeMap<Order, OrderDTO> orderToDTO = modelMapper.createTypeMap(Order.class, OrderDTO.class);
        orderToDTO.addMappings(m -> m.map(src -> src.getClient().getId(), OrderDTO::setClient));
        orderToDTO.addMappings(m -> m.map(src -> src.getMaster().getId(), OrderDTO::setMaster));

        TypeMap<OrderPart, OrderPartDTO> orderPartToDTO = modelMapper.createTypeMap(OrderPart.class, OrderPartDTO.class);
        orderPartToDTO.addMappings(m -> m.map(src -> src.getOrder().getId(), OrderPartDTO::setOrder));
        orderPartToDTO.addMappings(m -> m.map(src -> src.getPart().getId(), OrderPartDTO::setPart));

        return modelMapper;
    }
}
