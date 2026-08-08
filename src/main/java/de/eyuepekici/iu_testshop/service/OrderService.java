package de.eyuepekici.iu_testshop.service;

import de.eyuepekici.iu_testshop.model.CartItem;
import de.eyuepekici.iu_testshop.model.Order;
import de.eyuepekici.iu_testshop.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final ZoneId ORDER_TIME_ZONE = ZoneId.of("Europe/Berlin");

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(List<CartItem> cartItems, String paymentMethod, double totalPrice) {

        String productSummary = cartItems.stream()
                .map(item -> item.getQuantity() + "x " + item.getProduct().getName())
                .collect(Collectors.joining(", "));

        Order order = new Order(
                productSummary,
                paymentMethod,
                "Bestellung eingegangen",
                totalPrice,
                LocalDateTime.now(ORDER_TIME_ZONE)
        );

        return orderRepository.save(order);
    }
}