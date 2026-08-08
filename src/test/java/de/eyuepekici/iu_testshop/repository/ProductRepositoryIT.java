package de.eyuepekici.iu_testshop.repository;

import de.eyuepekici.iu_testshop.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ProductRepositoryIT {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldSaveAndLoadProduct() {
        Product product = new Product(
                "Test Laptop",
                1299.99,
                "Integration Test"
        );

        productRepository.save(product);

        List<Product> products = productRepository.findAll();

        assertFalse(products.isEmpty());
        assertTrue(
                products.stream()
                        .anyMatch(p -> p.getName().equals("Test Laptop"))
        );
    }

    @Test
    void shouldFindProductByName() {
        Product product = new Product(
                "Gaming Laptop",
                1499.99,
                "Gaming Gerät"
        );

        productRepository.save(product);

        List<Product> result =
                productRepository.findByNameContainingIgnoreCase("Laptop");

        assertFalse(result.isEmpty());
        assertTrue(
                result.stream()
                        .anyMatch(p -> p.getName().equals("Gaming Laptop"))
        );
    }
}
