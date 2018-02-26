package com.codecool.enterpriseproject.service;

import com.codecool.enterpriseproject.model.Message;
import com.codecool.enterpriseproject.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

@Service
public class MessageService extends BaseService {

    @Autowired
    MessageRepository messageRepository;


    public List<Message> getMessages(long threadId) {
        return messageRepository.getMessagesByChatBoxId(threadId);
    }
}
