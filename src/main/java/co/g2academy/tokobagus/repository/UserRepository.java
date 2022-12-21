package co.g2academy.tokobagus.repository;

import co.g2academy.tokobagus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User getUserByEmail(String email);
}
