package co.g2academy.tokobagus.repository;

import co.g2academy.tokobagus.model.Product;
import co.g2academy.tokobagus.model.User;
import co.g2academy.tokobagus.shoppingcart.ShoppingCart;
import co.g2academy.tokobagus.shoppingcart.ShoppingCartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShoppingJdbcCartRepository {

    private static final String SELECT_BY_SHOPPING_CART =
            "select * from t_shopping_cart_item "
                    + "where id_shopping_cart = ?";
    private static final String SELECT_BY_USER =
            "select * from t_shopping_cart "
            + "where id_user = ?";
    private static final String INSERT =
            "insert into t_shopping_cart (id_user) value (?)";
    private static final String INSERT_ITEM =
            "insert into t_shopping_cart_item "
            + "(id_shopping_cart, id_product, price, quantity) "
            + "values(?, ?, ?, ?)";
    private static final String DELETE =
            "delete from t_shopping_cart where id = ?";
    private static final String DELETE_ITEM =
            "delete from t_shopping_cart_item where id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private RowMapper<ShoppingCart> rowMapper = new BeanPropertyRowMapper<>(ShoppingCart.class);
    private RowMapper<ShoppingCartItem> rowMapperShoppingCartItem = new BeanPropertyRowMapper<>(ShoppingCartItem.class);

    public ShoppingCart getShoppingCart(User logginUser) {
        ShoppingCart sc = getShoppingCartByUser(logginUser);
        if (sc != null) {
            List<ShoppingCartItem> items = jdbcTemplate.query(SELECT_BY_SHOPPING_CART, rowMapperShoppingCartItem, sc.getId());
            sc.setItems(items);
        }
        return sc;
    }

    public void saveShoppingCart(User user) {
        jdbcTemplate.update(INSERT, user.getId());
    }
    public void saveShoppingCartItem(ShoppingCart sc, Product product, Integer quantity) {
        jdbcTemplate.update(INSERT_ITEM, sc.getId(), product.getId(), product.getPrice(), quantity);
    }
    public ShoppingCart getShoppingCartByUser(User logginUser) {
        ShoppingCart sc = null;
        try{
            sc = jdbcTemplate.queryForObject(
                    SELECT_BY_USER, rowMapper, logginUser.getId());
        } catch(Exception ex){
            //intentionally left blank
        }
        return sc;
    }
    public void delete(ShoppingCart sc) {
        for (ShoppingCartItem item : sc.getItems()) {
            jdbcTemplate.update(DELETE_ITEM, item.getId());
        }
        jdbcTemplate.update(DELETE, sc.getId());
        //jdbcTemplate.update(DELETE);
    }
}
