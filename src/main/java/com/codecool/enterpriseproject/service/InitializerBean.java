package com.codecool.enterpriseproject.service;

import com.codecool.enterpriseproject.model.User;
import com.codecool.enterpriseproject.model.ChatBox;
import org.springframework.stereotype.Component;

@Component
public class InitializerBean {

    private String hashedPassword = "$2a$10$XWDZyDhV1zY1eRsaY6ysg.k2jhbRXWowEpTyxaQArq1gh5rL2CIVq";

    public InitializerBean(UserService userService, ChatBoxService chatBoxService) {
        userService.addUser(new User("John", "Doe", "john@email.com", 37, hashedPassword, 1, "male", "female", true));
        userService.addUser(new User("Carla", "Jackson", "carla@email.com", 26, hashedPassword, 2, "female", "male", false));
        userService.addUser(new User("Clare", "Fraeser", "clare@email.com", 31, hashedPassword, 5, "female", "male", false));
        userService.addUser(new User("Mike", "Gregg", "mike@email.com", 22, hashedPassword, 8, "male", "female", false));
        userService.addUser(new User("Layla", "Jackson", "layla@email.com", 26, hashedPassword, 2, "female", "male", false));
        userService.addUser(new User("Maria", "lopez", "maria@email.com", 30, hashedPassword, 5, "female", "male", true));

        chatBoxService.addChatBox(new ChatBox(userService.findUserById(1), userService.findUserById(6)));
    }
}