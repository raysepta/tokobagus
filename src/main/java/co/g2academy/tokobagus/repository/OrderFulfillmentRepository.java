package co.g2academy.tokobagus.repository;

import co.g2academy.tokobagus.model.OrderFulfilment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderFulfillmentRepository extends JpaRepository<OrderFulfilment, Integer> {
}
