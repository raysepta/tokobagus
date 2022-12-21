package co.g2academy.tokobagus.repository;

import co.g2academy.tokobagus.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> getProductByNameContains(String searchQuery);
}
