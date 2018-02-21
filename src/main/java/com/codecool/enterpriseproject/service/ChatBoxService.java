package com.codecool.enterpriseproject.service;

import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.User;

import javax.persistence.EntityManager;
import com.codecool.enterpriseproject.model.Message;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class ChatBoxService extends BaseService {


    public List findPastChatBoxes(EntityManager em, User user) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createNamedQuery( "chatBox.getUsersWeMet", ChatBox.class );
        query.setParameter( "user", user );
        List usersWeMet = query.getResultList();
        transaction.commit();
        return usersWeMet;
    }


    public ChatBox getChatBox(User user, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createNamedQuery( "chatbox.getChatBox", ChatBox.class );
        query.setParameter( "user", user );
        List chatbox = query.getResultList();
        Object obj = null;
        if (!chatbox.isEmpty()) {
            obj = chatbox.get( 0 );
        }
        transaction.commit();
        em.close();
        return (ChatBox) obj;
    }

    public ChatBox getChatBoxById(int id, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createNamedQuery("ChatBox.getChatBoxById", ChatBox.class);
        query.setParameter("id", id);
        List chatbox = query.getResultList();
        Object obj = null;
        if (!chatbox.isEmpty()) {
            obj = chatbox.get( 0 );
        }
        transaction.commit();
        em.close();
        return (ChatBox) obj;
    }

    public void deactivateChatBox(EntityManagerFactory emf, ChatBox chatBox) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        ChatBox mergedChatBox = em.merge(chatBox);
        transaction.begin();
        mergedChatBox.deactivateChatBox();
        transaction.commit();
        em.close();

    }

}
