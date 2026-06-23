package de.eyuepekici.iu_testshop.controller;

import de.eyuepekici.iu_testshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String products(@RequestParam(required = false) String keyword,
                           Model model) {

        model.addAttribute("products", productService.searchProducts(keyword));
        model.addAttribute("keyword", keyword);

        return "products";
    }
}