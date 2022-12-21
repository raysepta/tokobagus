package co.g2academy.tokobagus.repository;

import co.g2academy.tokobagus.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcRepository {
    private static final String SELECT_BY_EMAIL =
            "select * from t_user where email = ?";
    private static final String INSERT =
            "insert into t_user(name, email, password) "
                    + "value (?, ?, ?)";

    @Autowired
    private JdbcTemplate productTemplate;
    private RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);

    public User getUserByEmail(String email) {
        try {
            return productTemplate.queryForObject(SELECT_BY_EMAIL, rowMapper, email);
        } catch (Exception ex) {
            return null;
        }
    }

    public void save(User user) {
        productTemplate.update(INSERT, user.getName(), user.getEmail(),
                user.getPassword());
    }

}




