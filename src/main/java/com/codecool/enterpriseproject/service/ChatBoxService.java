package com.codecool.enterpriseproject.service;

import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.User;
import com.codecool.enterpriseproject.repository.ChatBoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatBoxService{

    @Autowired
    ChatBoxRepository chatBoxRepository;


    public List<ChatBox> findPastChatBoxes(User user) {
        return chatBoxRepository.getChatBoxesByFirstUser(user);
    }

    public void addChatBox(ChatBox chatBox) {
        chatBoxRepository.save(chatBox);
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
