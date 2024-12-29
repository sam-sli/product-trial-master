package com.alten.shop.back.controller;

import com.alten.shop.back.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @GetMapping
    public Object getCart() {
        return cartService.getCartForCurrentUser();
    }



    @PostMapping("/add/{productId}")
    public void addProductToCart(@PathVariable Long productId) {
        cartService.addProductToCart(productId);
    }

    @DeleteMapping("/remove/{productId}")
    public void removeProductFromCart(@PathVariable Long productId) {
        cartService.removeProductFromCart(productId);
    }
}