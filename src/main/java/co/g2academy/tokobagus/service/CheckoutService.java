package co.g2academy.tokobagus.service;

import co.g2academy.tokobagus.model.Order;
import co.g2academy.tokobagus.model.OrderItem;
import co.g2academy.tokobagus.model.User;
import co.g2academy.tokobagus.repository.OrderJdbcRepository;
import co.g2academy.tokobagus.repository.OrderRepository;
import co.g2academy.tokobagus.repository.ShoppingCartRepository;
import co.g2academy.tokobagus.repository.ShoppingJdbcCartRepository;
import co.g2academy.tokobagus.shoppingcart.ShoppingCart;
import co.g2academy.tokobagus.shoppingcart.ShoppingCartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CheckoutService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order save(User user, ShoppingCart sc) {
        Order order = new Order();
        order.setTrxDate(new Date());
        Integer totalPrice = 0;
        Integer totalQuantity = 0;
        List<OrderItem> items = new ArrayList<>();
        for (ShoppingCartItem item : sc.getItems()) {
            totalPrice += item.getPrice() * item.getQuantity();
            totalQuantity += item.getQuantity();
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(item.getProduct());
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setOrder(order);
            items.add(orderItem);
        }
        order.setItems(items);
        order.setTotalPrice(totalPrice);
        order.setTotalQuantity(totalQuantity);
        order.setUser(user);
        orderRepository.save(order);
        shoppingCartRepository.delete(sc);
        return order;
    }
}
