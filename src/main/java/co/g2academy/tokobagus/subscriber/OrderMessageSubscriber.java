package co.g2academy.tokobagus.subscriber;

import co.g2academy.tokobagus.dto.OrderFulfillmentDto;
import co.g2academy.tokobagus.dto.OrderItemFulfillmentDto;
import co.g2academy.tokobagus.model.OrderFulfilment;
import co.g2academy.tokobagus.model.OrderItemFulfillment;
import co.g2academy.tokobagus.repository.OrderFulfillmentRepository;
import co.g2academy.tokobagus.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderMessageSubscriber implements MessageListener {
    private ObjectMapper mapper = new JsonMapper();

    @Autowired
    private OrderFulfillmentRepository repository;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String messageBody = new String(message.getBody());
        try {
            OrderFulfillmentDto orderFulfillmentDto = mapper.readValue(messageBody, OrderFulfillmentDto.class);
            OrderFulfilment orderFulfilment = convert(orderFulfillmentDto);
            repository.save(orderFulfilment);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
    }

    private OrderFulfilment convert(OrderFulfillmentDto orderFulfillmentDto) {
        OrderFulfilment orderFulfilment = OrderFulfilment.builder()
                .id(orderFulfillmentDto.getId())
                .userId(orderFulfillmentDto.getUserId())
                .orderId(orderFulfillmentDto.getStoreFrontOrderId())
                .orderDate(orderFulfillmentDto.getOrderDate())
                .trxDate(orderFulfillmentDto.getTrxDate())
                .status(orderFulfillmentDto.getStatus())
                .totalPrice(orderFulfillmentDto.getTotalPrice())
                .totalQuantity(orderFulfillmentDto.getTotalQuantity())
                .build();
        List<OrderItemFulfillment> orderItemFulfillmentList = new ArrayList<>();
        for (OrderItemFulfillmentDto orderItemFulfillmentDto : orderFulfillmentDto.getItems()) {
            OrderItemFulfillment orderItemFulfillment = OrderItemFulfillment.builder()
                    .id(orderItemFulfillmentDto.getId())
                    .productId(orderItemFulfillmentDto.getProductId())
                    .orderItemId(orderItemFulfillmentDto.getStoreFrontOrderItemId())
                    .productName(orderItemFulfillmentDto.getProductName())
                    .price(orderItemFulfillmentDto.getPrice())
                    .quantity(orderItemFulfillmentDto.getQuantity())
                    .orderFulfillment(orderFulfilment)
                    .build();
            orderItemFulfillmentList.add(orderItemFulfillment);
        }
        orderFulfilment.setOrderItemFulfillmentList(orderItemFulfillmentList);
        return orderFulfilment;
    }
}
