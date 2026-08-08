package de.eyuepekici.iu_testshop.service;

import de.eyuepekici.iu_testshop.model.Product;
import de.eyuepekici.iu_testshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Test
    void addProduct_shouldAddProductToCart() {
        ProductRepository repository = mock(ProductRepository.class);
        Product laptop = product(1L, "Laptop", 999.99);
        when(repository.findById(1L)).thenReturn(Optional.of(laptop));

        CartService cartService = new CartService(repository);
        cartService.addProduct(1L);

        assertEquals(1, cartService.getItems().size());
        assertEquals(1, cartService.getItems().getFirst().getQuantity());
    }

    @Test
    void addProduct_shouldIncreaseQuantity_whenProductAlreadyExists() {
        ProductRepository repository = mock(ProductRepository.class);
        Product laptop = product(1L, "Laptop", 999.99);
        when(repository.findById(1L)).thenReturn(Optional.of(laptop));

        CartService cartService = new CartService(repository);
        cartService.addProduct(1L);
        cartService.addProduct(1L);

        assertEquals(1, cartService.getItems().size());
        assertEquals(2, cartService.getItems().getFirst().getQuantity());
        assertEquals(1999.98, cartService.getTotalPrice(), 0.001);
    }

    @Test
    void addProduct_shouldThrowException_whenProductDoesNotExist() {
        ProductRepository repository = mock(ProductRepository.class);
        when(repository.findById(99L)).thenReturn(Optional.empty());

        CartService cartService = new CartService(repository);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cartService.addProduct(99L)
        );

        assertEquals("Produkt nicht gefunden", exception.getMessage());
        assertTrue(cartService.getItems().isEmpty());
    }

    @Test
    void totalPrice_shouldCalculateCorrectly() {
        ProductRepository repository = mock(ProductRepository.class);
        Product laptop = product(1L, "Laptop", 999.99);
        when(repository.findById(1L)).thenReturn(Optional.of(laptop));

        CartService cartService = new CartService(repository);
        cartService.addProduct(1L);

        assertEquals(999.99, cartService.getTotalPrice(), 0.001);
    }

    @Test
    void removeProduct_shouldRemoveProductFromCart() {
        ProductRepository repository = mock(ProductRepository.class);
        Product laptop = product(1L, "Laptop", 999.99);
        when(repository.findById(1L)).thenReturn(Optional.of(laptop));

        CartService cartService = new CartService(repository);
        cartService.addProduct(1L);
        cartService.removeProduct(1L);

        assertTrue(cartService.getItems().isEmpty());
    }

    @Test
    void clearCart_shouldRemoveAllProducts() {
        ProductRepository repository = mock(ProductRepository.class);
        Product laptop = product(1L, "Laptop", 999.99);
        Product monitor = product(2L, "Monitor", 249.99);
        when(repository.findById(1L)).thenReturn(Optional.of(laptop));
        when(repository.findById(2L)).thenReturn(Optional.of(monitor));

        CartService cartService = new CartService(repository);
        cartService.addProduct(1L);
        cartService.addProduct(2L);

        cartService.clearCart();

        assertTrue(cartService.getItems().isEmpty());
        assertEquals(0.0, cartService.getTotalPrice(), 0.001);
    }

    private Product product(Long id, String name, double price) {
        Product product = new Product(name, price, "Testbeschreibung");
        product.setId(id);
        return product;
    }
}
