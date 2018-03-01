package com.codecool.enterpriseproject.repository;

import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatBoxRepository extends JpaRepository<ChatBox, Long> {

    List<ChatBox> findChatBoxesByFirstUserAndActiveTrueOrSecondUserAndActiveTrue(User user, User sUser);
    List<ChatBox> getChatBoxesByFirstUser(User user);

}
