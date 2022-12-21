package co.g2academy.tokobagus.repository;

import co.g2academy.tokobagus.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductJdbcRepository {
    private static final String SELECT =
            "select id, name, price, stock from t_product";
    private static final String SELECT_BY_ID =
            "select * from t_product where id = ?";
    private static final String INSERT =
            "insert into t_product(name, stock, price) "
            + "value (?, ?, ?)";
    private static final String UPDATE =
            "update t_product set name = ?, stock = ?, price =?"
            + "where id = ?";
    private static final String DELETE =
            "delete from t_product";
    private static final String DELETE_BY_ID =
            "delete from t_product where id = ?";
    @Autowired
    private JdbcTemplate productTemplate;
    private RowMapper<Product> rowMapper = new BeanPropertyRowMapper<>(Product.class);

    public List<Product> getProduct() {
        return productTemplate.query(SELECT, rowMapper);
    }
    public Product getProductById(Integer id) {
        return productTemplate.queryForObject(SELECT_BY_ID, rowMapper, id);
    }
    public void save(Product product) {
        if (product.getId() == null ) {
            productTemplate.update(INSERT, product.getName(), product.getStock(),
                    product.getPrice());
        } else {
            productTemplate.update(UPDATE, product.getName(), product.getStock(),
                    product.getPrice(), product.getId());
        }
    }
    public void delete() {
        productTemplate.update(DELETE);
    }
    public void delete(Integer id) {
        productTemplate.update(DELETE_BY_ID, id);
    }
}
