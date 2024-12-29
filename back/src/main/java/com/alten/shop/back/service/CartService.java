package com.alten.shop.back.service;

import com.alten.shop.back.model.Cart;
import com.alten.shop.back.model.Product;
import com.alten.shop.back.model.UserPrincipal;
import com.alten.shop.back.model.Users;
import com.alten.shop.back.repository.CartRepository;
import com.alten.shop.back.repository.ProductRepository;
import com.alten.shop.back.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Cart getCartForCurrentUser() {
        String username = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Users user = userRepository.findByEmail(username);
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            // If no cart exists, create and save a new cart
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }
        return cart;
    }

    public void addProductToCart(Long productId) {
        Cart cart = getCartForCurrentUser();
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        cart.getProducts().add(product);
        cartRepository.save(cart);
    }

    public void removeProductFromCart(Long productId) {
        Cart cart = getCartForCurrentUser();
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        cart.getProducts().remove(product);
        cartRepository.save(cart);
    }
}