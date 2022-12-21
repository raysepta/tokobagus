package co.g2academy.tokobagus.controller;

import co.g2academy.tokobagus.model.OrderFulfilment;
import co.g2academy.tokobagus.model.OrderItemFulfillment;
import co.g2academy.tokobagus.repository.OrderFulfillmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderFulfillmentRepository orderFulfillmentRepository;


    @GetMapping("/order/{id}")
    public OrderFulfilment getOrderFulfillmentById(@PathVariable Integer id) {
        OrderFulfilment orderFulfilment = orderFulfillmentRepository.findById(id).get();
        if (orderFulfilment != null) {
            for (OrderItemFulfillment itemFulfillment : orderFulfilment.getOrderItemFulfillmentList()) {
                itemFulfillment.setOrderFulfillment(null);
            }
        }
        return orderFulfilment;
    }
}
