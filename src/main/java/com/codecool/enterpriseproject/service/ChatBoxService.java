package com.codecool.enterpriseproject.service;

import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.User;

import javax.persistence.EntityManager;
import com.codecool.enterpriseproject.model.Message;
import com.codecool.enterpriseproject.repository.ChatBoxRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class ChatBoxService extends BaseService {

    @Autowired
    ChatBoxRepository chatBoxRepository;

    public List<ChatBox> findPastChatBoxes(User user) {
        return chatBoxRepository.getChatBoxesByFirstUser(user);
    }


    public ChatBox getChatBox(User user) {
        Long id = user.getId();
        return chatBoxRepository.getChatBoxByFirstUserOrSecondUserIdAndActiveTrue(user, id);
    }

    public ChatBox getChatBoxById(long id) {
        return chatBoxRepository.getOne(id);
    }

    public void deactivateChatBox(ChatBox chatBox) {
        chatBox.deactivateChatBox();
        chatBoxRepository.save(chatBox);
    }

}
