package co.g2academy.tokobagus.repository;

import co.g2academy.tokobagus.model.Order;
import co.g2academy.tokobagus.model.OrderItem;
import co.g2academy.tokobagus.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class OrderJdbcRepository {

    private static final String INSERT_ORDER =
            "insert into t_order(id_user, trx_date, total_price, total_quantity) "
            + "values (?, ?, ?, ?)";
    private static final String INSERT_ITEM =
            "insert into t_order_item(id_order, id_product, price, quantity) "
            + "values(?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(User user, Order order) {

        PreparedStatementCreator creator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, user.getId());
                ps.setDate(2, new java.sql.Date(order.getTrxDate().getTime()));
                ps.setInt(3, order.getTotalPrice());
                ps.setInt(4, order.getTotalQuantity());
                return ps;
            }
        };

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(creator, keyHolder);
        Integer orderId = keyHolder.getKey().intValue();
        for (OrderItem item: order.getItems()) {
            jdbcTemplate.update(INSERT_ITEM, orderId, item.getProduct().getId(), item.getPrice(), item.getQuantity());

        }

    }
}
