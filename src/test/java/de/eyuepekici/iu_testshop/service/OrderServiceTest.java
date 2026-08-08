package de.eyuepekici.iu_testshop.service;

import de.eyuepekici.iu_testshop.model.CartItem;
import de.eyuepekici.iu_testshop.model.Order;
import de.eyuepekici.iu_testshop.model.Product;
import de.eyuepekici.iu_testshop.repository.OrderRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Test
    void createOrder_shouldCreateProductSummaryAndPersistOrder() {
        OrderRepository repository = mock(OrderRepository.class);
        OrderService service = new OrderService(repository);

        Product laptop = new Product("Laptop", 999.99, "Business Laptop");
        Product mouse = new Product("Mouse", 49.99, "Wireless Mouse");
        List<CartItem> items = List.of(
                new CartItem(laptop, 1),
                new CartItem(mouse, 2)
        );

        when(repository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order order = service.createOrder(items, "PAYPAL", 1099.97);

        assertEquals("1x Laptop, 2x Mouse", order.getProducts());
        assertEquals("PAYPAL", order.getPaymentMethod());
        assertEquals("Bestellung eingegangen", order.getStatus());
        assertEquals(1099.97, order.getTotalPrice(), 0.001);
        assertNotNull(order.getCreatedAt());
        verify(repository).save(any(Order.class));
    }

    @Test
    void createOrder_shouldHandleEmptyCart() {
        OrderRepository repository = mock(OrderRepository.class);
        OrderService service = new OrderService(repository);
        when(repository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order order = service.createOrder(List.of(), "PAYPAL", 0.0);

        assertEquals("", order.getProducts());
        assertEquals(0.0, order.getTotalPrice(), 0.001);
        verify(repository).save(any(Order.class));
    }

    @Test
    void createOrder_shouldReturnRepositoryResult() {
        OrderRepository repository = mock(OrderRepository.class);
        OrderService service = new OrderService(repository);
        Order persisted = new Order();
        persisted.setId(42L);
        when(repository.save(any(Order.class))).thenReturn(persisted);

        Order result = service.createOrder(List.of(), "CARD", 15.0);

        assertSame(persisted, result);
        assertEquals(42L, result.getId());
    }
}
