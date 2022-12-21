package co.g2academy.tokobagus.repository;

import co.g2academy.tokobagus.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
