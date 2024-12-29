package com.alten.shop.back.repository;

import com.alten.shop.back.model.Cart;
import com.alten.shop.back.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUser(Users user);
}
