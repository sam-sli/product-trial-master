package com.alten.shop.back.service;

import com.alten.shop.back.model.Product;
import com.alten.shop.back.model.UserPrincipal;
import com.alten.shop.back.model.Users;
import com.alten.shop.back.repository.ProductRepository;
import org.apache.catalina.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {

        if (!isAdmin()) {
            throw new SecurityException("Vous n'avez pas les droits pour ajouter un produit.");
        }
        return productRepository.save(product);
    }

    public Product update(Long id, Product product) {
        if (!isAdmin()) {
            throw new SecurityException("Vous n'avez pas les droits pour modifier un produit.");
        }

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setInventoryStatus(product.getInventoryStatus());

        return productRepository.save(existingProduct);
    }

    public void delete(Long id) {
        if (!isAdmin()) {
            throw new SecurityException("Vous n'avez pas les droits pour supprimer un produit.");
        }
        productRepository.deleteById(id);
    }


    private boolean isAdmin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrincipal) {
            String email = ((UserPrincipal) principal).getUsername();
            return "admin@admin.com".equals(email);
        }
        return false;
    }
}