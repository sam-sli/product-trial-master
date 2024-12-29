package com.alten.shop.back.controller;

import com.alten.shop.back.model.Users;
import com.alten.shop.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService service;


    @PostMapping("/account")
    public Users register(@RequestBody Users user) {
        return service.register(user);

    }

    @PostMapping("/token")
    public String login(@RequestBody Users user) {

        return service.verify(user);
    }
}
