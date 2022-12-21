package co.g2academy.tokobagus.controller;

import co.g2academy.tokobagus.model.Product;
import co.g2academy.tokobagus.model.User;
import co.g2academy.tokobagus.repository.*;
import co.g2academy.tokobagus.service.ShoppingCartService;
import co.g2academy.tokobagus.shoppingcart.AddToCart;
import co.g2academy.tokobagus.shoppingcart.ShoppingCart;
import co.g2academy.tokobagus.shoppingcart.ShoppingCartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestBody AddToCart addToCart, Principal principal) {
        User user = userRepository.getUserByEmail(principal.getName());
        Product product = productRepository.findById(addToCart.getProductId()).get();
        if (product != null && user != null) {
            shoppingCartService.addToCart(user, product, addToCart.getQuantity());
            return "success";
        } else {
            return "fail";
        }
    }
    @GetMapping("/shopping-cart")
    public ShoppingCart getShoppingCart(Principal principal) {
        User user = userRepository.getUserByEmail(principal.getName());
        if (user != null) {
            ShoppingCart sc = shoppingCartRepository.getShoppingCartByUser(user);
            if (sc != null) {
                for (ShoppingCartItem item : sc.getItems()) {
                    item.setCart(null);
                    item.getProduct().setUser(null);
                }
                sc.setUser(null);
            }
            return sc;
        }
        return null;
    }
}
