package com.codecool.enterpriseproject.service;

import com.codecool.enterpriseproject.model.Message;
import com.codecool.enterpriseproject.repository.MessageRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class MessageService extends BaseService {

    MessageRepository messageRepository;


    public List<Message> getMessages(long threadId) {
        return messageRepository.getAllByChatBoxId(threadId);
    }
}
