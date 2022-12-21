package co.g2academy.tokobagus.controller;

import co.g2academy.tokobagus.model.Order;
import co.g2academy.tokobagus.model.OrderItem;
import co.g2academy.tokobagus.model.User;
import co.g2academy.tokobagus.repository.DummyLoginRepository;
import co.g2academy.tokobagus.repository.ShoppingCartRepository;
import co.g2academy.tokobagus.repository.ShoppingJdbcCartRepository;
import co.g2academy.tokobagus.repository.UserRepository;
import co.g2academy.tokobagus.service.CheckoutService;
import co.g2academy.tokobagus.shoppingcart.ShoppingCart;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class CheckoutController {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CheckoutService checkoutService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private ChannelTopic topic = new ChannelTopic("orderMessageQueue");
    private ObjectMapper mapper = new JsonMapper();


    @PostMapping("/checkout")
    public String checkout(Principal principal) {
        User user = userRepository.getUserByEmail(principal.getName());
        if (user != null) {
            ShoppingCart sc = shoppingCartRepository.getShoppingCartByUser(user);
            if (sc != null) {
                Order order = checkoutService.save(user, sc);
                //send message to Redis PubSub
                publishMessageToRedisPubsub(order);
                return "success";
            }
        }
        return "fail";
    }

    private void publishMessageToRedisPubsub(Order order) {
        order.getUser().setPassword(null);
        for (OrderItem item : order.getItems()) {
            item.setOrder(null);
            item.getProduct().setUser(null);
        }
        try {
            String jsonMessageBody = mapper.writeValueAsString(order);
            redisTemplate.convertAndSend(topic.getTopic(), jsonMessageBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
