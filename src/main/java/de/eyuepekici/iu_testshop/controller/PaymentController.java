package de.eyuepekici.iu_testshop.controller;

import de.eyuepekici.iu_testshop.service.CartService;
import de.eyuepekici.iu_testshop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PaymentController {

    private final CartService cartService;
    private final OrderService orderService;

    public PaymentController(CartService cartService,
                             OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @GetMapping("/payment")
    public String payment(Model model) {

        if (cartService.getItems().isEmpty()) {
            return "redirect:/cart";
        }

        model.addAttribute("totalPrice", cartService.getTotalPrice());

        return "payment";
    }

    @PostMapping("/payment/confirm")
    public String confirmPayment(@RequestParam String paymentMethod) {

        if (!cartService.getItems().isEmpty()) {
            orderService.createOrder(
                    cartService.getItems(),
                    paymentMethod,
                    cartService.getTotalPrice()
            );

            cartService.clearCart();
        }

        return "redirect:/success";
    }
}