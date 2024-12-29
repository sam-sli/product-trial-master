package com.alten.shop.back.service;

import com.alten.shop.back.model.WishList;
import com.alten.shop.back.model.Product;
import com.alten.shop.back.model.UserPrincipal;
import com.alten.shop.back.model.Users;
import com.alten.shop.back.repository.WishListRepository;
import com.alten.shop.back.repository.ProductRepository;
import com.alten.shop.back.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final WishListRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public WishListService(WishListRepository wishlistRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public WishList getWishlistForCurrentUser() {
        String username = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Users user = userRepository.findByEmail(username);

        WishList wishList = wishlistRepository.findByUser(user);
        if (wishList == null) {
            // If no cart exists, create and save a new cart
            wishList = new WishList();
            wishList.setUser(user);
            wishList = wishlistRepository.save(wishList);
        }
        return wishList;
    }

    public void addProductToWishlist(Long productId) {
        WishList wishlist = getWishlistForCurrentUser();
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        wishlist.getProducts().add(product);
        wishlistRepository.save(wishlist);
    }

    public void removeProductFromWishlist(Long productId) {
        WishList wishlist = getWishlistForCurrentUser();
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        wishlist.getProducts().remove(product);
        wishlistRepository.save(wishlist);
    }
}
