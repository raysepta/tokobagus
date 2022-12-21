package co.g2academy.tokobagus.repository;

import co.g2academy.tokobagus.model.User;
import co.g2academy.tokobagus.shoppingcart.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

    public ShoppingCart getShoppingCartByUser(User user);
}
