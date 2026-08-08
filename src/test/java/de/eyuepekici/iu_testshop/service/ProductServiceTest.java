package de.eyuepekici.iu_testshop.service;

import de.eyuepekici.iu_testshop.model.Product;
import de.eyuepekici.iu_testshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Test
    void searchProducts_shouldReturnAllProducts_whenKeywordIsEmpty() {
        ProductRepository productRepository = mock(ProductRepository.class);
        ProductService productService = new ProductService(productRepository);

        when(productRepository.findAll()).thenReturn(List.of(
                new Product("Laptop", 999.99, "Business Laptop"),
                new Product("Monitor", 249.99, "27 Zoll Monitor")
        ));

        List<Product> result = productService.searchProducts("");

        assertEquals(2, result.size());
        verify(productRepository).findAll();
        verify(productRepository, never()).findByNameContainingIgnoreCase(anyString());
    }

    @Test
    void searchProducts_shouldReturnAllProducts_whenKeywordIsNull() {
        ProductRepository productRepository = mock(ProductRepository.class);
        ProductService productService = new ProductService(productRepository);
        when(productRepository.findAll()).thenReturn(List.of(
                new Product("Laptop", 999.99, "Business Laptop")
        ));

        List<Product> result = productService.searchProducts(null);

        assertEquals(1, result.size());
        verify(productRepository).findAll();
    }

    @Test
    void searchProducts_shouldReturnMatchingProducts_whenKeywordIsGiven() {
        ProductRepository productRepository = mock(ProductRepository.class);
        ProductService productService = new ProductService(productRepository);

        when(productRepository.findByNameContainingIgnoreCase("Laptop")).thenReturn(List.of(
                new Product("Laptop", 999.99, "Business Laptop")
        ));

        List<Product> result = productService.searchProducts("Laptop");

        assertEquals(1, result.size());
        assertEquals("Laptop", result.getFirst().getName());
        verify(productRepository).findByNameContainingIgnoreCase("Laptop");
    }

    @Test
    void searchProducts_shouldReturnEmptyList_whenNoProductMatches() {
        ProductRepository productRepository = mock(ProductRepository.class);
        ProductService productService = new ProductService(productRepository);
        when(productRepository.findByNameContainingIgnoreCase("Tablet")).thenReturn(List.of());

        List<Product> result = productService.searchProducts("Tablet");

        assertTrue(result.isEmpty());
        verify(productRepository).findByNameContainingIgnoreCase("Tablet");
    }

    @Test
    void save_shouldDelegateToRepositoryAndReturnSavedProduct() {
        ProductRepository productRepository = mock(ProductRepository.class);
        ProductService productService = new ProductService(productRepository);
        Product product = new Product("Keyboard", 79.99, "Mechanical Keyboard");
        product.setId(7L);
        when(productRepository.save(product)).thenReturn(product);

        Product saved = productService.save(product);

        assertSame(product, saved);
        assertEquals(7L, saved.getId());
        verify(productRepository).save(product);
    }
}
