package com.codecool.enterpriseproject.service;

import com.codecool.enterpriseproject.model.User;
import org.springframework.stereotype.Component;

@Component
public class InitializerBean {

    public InitializerBean(UserService userService) {
        userService.addUser(new User("John", "Doe", "john@email.com", 37, "password", 1, "male", "female"));
        userService.addUser(new User("Carla", "Jackson", "carla@email.com", 26, "password", 2, "female", "male"));
        userService.addUser(new User("Clare", "Fraeser", "clare@email.com", 31, "password", 5, "female", "male"));
        userService.addUser(new User("Mike", "Gregg", "mike@email.com", 22, "password", 8, "male", "female"));
        userService.addUser(new User("Layla", "Jackson", "layla@email.com", 26, "password", 2, "female", "male"));
    }
}