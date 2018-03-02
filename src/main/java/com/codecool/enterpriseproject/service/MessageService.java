package com.codecool.enterpriseproject.service;

import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.Message;
import com.codecool.enterpriseproject.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public void addMessage(Message message) {
        messageRepository.save(message);
    }

    public List<Message> getMessages(ChatBox chatBox) {
        return messageRepository.findMessagesByChatBox_ActiveAndChatBox(true, chatBox);
    }
}
