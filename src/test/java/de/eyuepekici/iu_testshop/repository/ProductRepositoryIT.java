package de.eyuepekici.iu_testshop.repository;

import de.eyuepekici.iu_testshop.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        Product saved = productRepository.save(product);

        assertNotNull(saved.getId());
        assertTrue(productRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void shouldFindProductByName() {
        Product product = new Product(
                "Gaming Laptop",
                1499.99,
                "Gaming Gerät"
        );

        productRepository.save(product);

        List<Product> result = productRepository.findByNameContainingIgnoreCase("Laptop");

        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(p -> p.getName().equals("Gaming Laptop")));
    }

    @Test
    void shouldUpdateStoredProduct() {
        Product product = productRepository.save(new Product(
                "Office Monitor",
                199.99,
                "24 Zoll"
        ));

        product.setPrice(229.99);
        product.setDescription("27 Zoll");
        productRepository.save(product);

        Product updated = productRepository.findById(product.getId()).orElseThrow();

        assertEquals(229.99, updated.getPrice(), 0.001);
        assertEquals("27 Zoll", updated.getDescription());
    }

    @Test
    void shouldDeleteStoredProduct() {
        Product product = productRepository.save(new Product(
                "Temporary Product",
                10.0,
                "Will be deleted"
        ));
        Long id = product.getId();

        productRepository.deleteById(id);

        assertTrue(productRepository.findById(id).isEmpty());
    }
}
