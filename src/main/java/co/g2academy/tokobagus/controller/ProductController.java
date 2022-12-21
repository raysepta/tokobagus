package co.g2academy.tokobagus.controller;

import co.g2academy.tokobagus.model.Product;
import co.g2academy.tokobagus.model.User;
import co.g2academy.tokobagus.repository.DummyLoginRepository;
import co.g2academy.tokobagus.repository.ProductRepository;
import co.g2academy.tokobagus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/product")
    public List<Product> getProducts() {
        List<Product> products = repository.findAll();
        for (Product product: products) {
            product.setUser(null);
        }
        return products;
    }
    @GetMapping("/product/{id}")
    @Cacheable(value = "product", key = "#id")
    public Product getProductById(@PathVariable Integer id) {
        Product p =repository.findById(id).get();
        if (p != null) {
            p.setUser(null);
        }
        return p;
    }
    @GetMapping("/product/")
    @Cacheable(value = "search", key = "#searchQuery")
    public List<Product> search(@RequestParam String searchQuery) {
        List<Product> products = repository.getProductByNameContains(searchQuery);
        for (Product product : products) {
            product.setUser(null);
        }
        return products;
    }

    @PostMapping("/product")
    public String save(@RequestBody Product product, Principal principal) {
        User user = userRepository.getUserByEmail(principal.getName());
        product.setUser(user);
        repository.save(product);
        return "success";
    }
    @PutMapping("/product")
    public String update(@RequestBody Product product, Principal principal) {
        User user = userRepository.getUserByEmail(principal.getName());
        product.setUser(user);
        repository.save(product);
        return "success";
    }
    @PutMapping("/product/stock/{id}/{stock}")
    public String updateStock(@PathVariable Integer id, @PathVariable Integer stock, Principal principal) {
        User user = userRepository.getUserByEmail(principal.getName());
        Product product = repository.findById(id).get();
        if (product != null && user.getId().equals(product.getUser().getId())) {
            product.setStock(stock);
            repository.save(product);
            return "success";
        }
        return "fail";
    }

    @DeleteMapping("/product/{id}")
    public String delete(@PathVariable Integer id, Principal principal) {
        User user = userRepository.getUserByEmail(principal.getName());
        Product product = repository.findById(id).get();
        if (product != null && product.getUser().getId().equals(user.getId())) {
            repository.deleteById(id);
            return "success";
        }
        return "fail";
    }
    @DeleteMapping("/product")
    public String delete() {
        repository.deleteAll();
        return "success";
    }

}
