package com.alten.shop.back.repository;

import com.alten.shop.back.model.Users;
import com.alten.shop.back.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    WishList findByUser(Users user);
}
