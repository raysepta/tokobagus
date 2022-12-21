package co.g2academy.tokobagus.service;

import co.g2academy.tokobagus.model.Product;
import co.g2academy.tokobagus.model.User;
import co.g2academy.tokobagus.repository.ShoppingCartRepository;
import co.g2academy.tokobagus.repository.ShoppingJdbcCartRepository;
import co.g2academy.tokobagus.shoppingcart.ShoppingCart;
import co.g2academy.tokobagus.shoppingcart.ShoppingCartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository repository;

    @Transactional
    public void addToCart(User logginUser, Product product, Integer quantity) {
        //1. check if there is shopping cart with loggin user
        ShoppingCart sc = repository.getShoppingCartByUser(logginUser);
        //2. if there is no shopping cart with loggin user
        //   then insert shopping cart first then insert
        //   shopping cart item
        if (sc == null) {
            sc = new ShoppingCart();
            sc.setUser(logginUser);
            sc.setItems(new ArrayList<>());
            repository.save(sc);
        }
        //3. insert shopping cart item
        ShoppingCartItem item = new ShoppingCartItem();
        item.setCart(sc);
        item.setProduct(product);
        item.setPrice(product.getPrice());
        item.setQuantity(quantity);
        sc.getItems().add(item);
        repository.save(sc);
    }
}
