package co.g2academy.tokobagus.shoppingcart;

import co.g2academy.tokobagus.model.User;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<ShoppingCartItem> items;
    @OneToOne
    @JoinColumn(nullable = false, name = "id_user")
    private User user;

    public Integer getTotalPrice() {
        Integer totalPrice = 0;
        if (items != null) {
            for (ShoppingCartItem item : items) {
                totalPrice += item.getPrice() * item.getQuantity();
            }
        }
        return totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartItem> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
