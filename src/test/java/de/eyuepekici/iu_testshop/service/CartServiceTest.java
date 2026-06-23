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

        Product laptop = new Product(
                "Laptop",
                999.99,
                "Business Laptop"
        );

        laptop.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(laptop));

        CartService cartService =
                new CartService(repository);

        cartService.addProduct(1L);

        assertEquals(
                1,
                cartService.getItems().size()
        );
    }

    @Test
    void totalPrice_shouldCalculateCorrectly() {

        ProductRepository repository = mock(ProductRepository.class);

        Product laptop = new Product(
                "Laptop",
                999.99,
                "Business Laptop"
        );

        laptop.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(laptop));

        CartService cartService =
                new CartService(repository);

        cartService.addProduct(1L);

        assertEquals(
                999.99,
                cartService.getTotalPrice()
        );
    }

    @Test
    void removeProduct_shouldRemoveProductFromCart() {

        ProductRepository repository = mock(ProductRepository.class);

        Product laptop = new Product(
                "Laptop",
                999.99,
                "Business Laptop"
        );

        laptop.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(laptop));

        CartService cartService =
                new CartService(repository);

        cartService.addProduct(1L);
        cartService.removeProduct(1L);

        assertTrue(
                cartService.getItems().isEmpty()
        );
    }
}