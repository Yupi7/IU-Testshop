package de.eyuepekici.iu_testshop.controller;

import de.eyuepekici.iu_testshop.model.CartItem;
import de.eyuepekici.iu_testshop.model.Product;
import de.eyuepekici.iu_testshop.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CartControllerTest {

    @Test
    void addToCart_shouldAddProductAndRedirectToProducts() {
        CartService cartService = mock(CartService.class);
        CartController controller = new CartController(cartService);

        assertEquals("redirect:/products?added=true", controller.addToCart(1L));
        verify(cartService).addProduct(1L);
    }

    @Test
    void removeFromCart_shouldRemoveProductAndRedirectToCart() {
        CartService cartService = mock(CartService.class);
        CartController controller = new CartController(cartService);

        assertEquals("redirect:/cart", controller.removeFromCart(1L));
        verify(cartService).removeProduct(1L);
    }

    @Test
    void cart_shouldExposeItemsAndTotalPrice() {
        CartService cartService = mock(CartService.class);
        Product product = new Product("Laptop", 999.99, "Business Laptop");
        List<CartItem> items = List.of(new CartItem(product, 1));
        when(cartService.getItems()).thenReturn(items);
        when(cartService.getTotalPrice()).thenReturn(999.99);
        ExtendedModelMap model = new ExtendedModelMap();
        CartController controller = new CartController(cartService);

        assertEquals("cart", controller.cart(model));
        assertEquals(items, model.get("items"));
        assertEquals(999.99, model.get("totalPrice"));
    }
}
