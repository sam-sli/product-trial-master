package com.alten.shop.back.controller;

import com.alten.shop.back.service.WishListService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }


    @GetMapping
    public Object getWishlist() {
        return wishListService.getWishlistForCurrentUser();
    }

    @PostMapping("/add/{productId}")
    public void addProductToWishlist(@PathVariable Long productId) {
        wishListService.addProductToWishlist(productId);
    }


    @DeleteMapping("/remove/{productId}")
    public void removeProductFromWishlist(@PathVariable Long productId) {
        wishListService.removeProductFromWishlist(productId);
    }
}
