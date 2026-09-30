package de.eyuepekici.iu_testshop.controller;

import de.eyuepekici.iu_testshop.model.CartItem;
import de.eyuepekici.iu_testshop.model.Product;
import de.eyuepekici.iu_testshop.service.CartService;
import de.eyuepekici.iu_testshop.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PaymentControllerTest {

    @Test
    void payment_shouldRedirectToCart_whenCartIsEmpty() {
        CartService cartService = mock(CartService.class);
        OrderService orderService = mock(OrderService.class);
        when(cartService.getItems()).thenReturn(List.of());

        PaymentController controller = new PaymentController(cartService, orderService);

        assertEquals("redirect:/cart", controller.payment(new ExtendedModelMap()));
    }

    @Test
    void payment_shouldShowTotalPrice_whenCartContainsItems() {
        CartService cartService = mock(CartService.class);
        OrderService orderService = mock(OrderService.class);
        Product product = new Product("Laptop", 999.99, "Business Laptop");
        List<CartItem> items = List.of(new CartItem(product, 1));
        when(cartService.getItems()).thenReturn(items);
        when(cartService.getTotalPrice()).thenReturn(999.99);
        ExtendedModelMap model = new ExtendedModelMap();

        PaymentController controller = new PaymentController(cartService, orderService);

        assertEquals("payment", controller.payment(model));
        assertEquals(999.99, model.get("totalPrice"));
    }

    @Test
    void confirmPayment_shouldCreateOrderAndClearCart_whenCartContainsItems() {
        CartService cartService = mock(CartService.class);
        OrderService orderService = mock(OrderService.class);
        Product product = new Product("Laptop", 999.99, "Business Laptop");
        List<CartItem> items = List.of(new CartItem(product, 1));
        when(cartService.getItems()).thenReturn(items);
        when(cartService.getTotalPrice()).thenReturn(999.99);

        PaymentController controller = new PaymentController(cartService, orderService);

        assertEquals("redirect:/success", controller.confirmPayment("PAYPAL"));
        verify(orderService).createOrder(items, "PAYPAL", 999.99);
        verify(cartService).clearCart();
    }

    @Test
    void confirmPayment_shouldNotCreateOrder_whenCartIsEmpty() {
        CartService cartService = mock(CartService.class);
        OrderService orderService = mock(OrderService.class);
        when(cartService.getItems()).thenReturn(List.of());

        PaymentController controller = new PaymentController(cartService, orderService);

        assertEquals("redirect:/success", controller.confirmPayment("PAYPAL"));
        verifyNoInteractions(orderService);
        verify(cartService, never()).clearCart();
    }
}
