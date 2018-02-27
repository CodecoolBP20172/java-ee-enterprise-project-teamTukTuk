package com.codecool.enterpriseproject.service;

import com.codecool.enterpriseproject.model.User;
import org.springframework.stereotype.Component;

@Component
public class InitializerBean {

    public InitializerBean(UserService userService) {
        userService.addUser(new User("John", "Doe", "john@email.com", 37, "password", 1, "male", "female"));
    }
}