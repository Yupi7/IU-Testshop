package de.eyuepekici.iu_testshop.controller;

import de.eyuepekici.iu_testshop.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id) {
        cartService.addProduct(id);
        return "redirect:/products?added=true";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cartService.removeProduct(id);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("items", cartService.getItems());
        model.addAttribute("totalPrice", cartService.getTotalPrice());
        return "cart";
    }
}