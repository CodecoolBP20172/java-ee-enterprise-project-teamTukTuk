package com.codecool.enterpriseproject.dbhandler;

import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.User;

import javax.persistence.EntityManager;
import com.codecool.enterpriseproject.model.Message;
import com.codecool.enterpriseproject.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class ChatBoxDbHandler {
    public void addNewChatBox(EntityManagerFactory emf, ChatBox chatBox) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(chatBox);
        transaction.commit();
        em.close();
    }

    public List findPastChatBoxes(EntityManager em, User user) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createNamedQuery( "chatBox.getUsersWeMet", ChatBox.class );
        query.setParameter( "user", user );
        List usersWeMet = query.getResultList();
        transaction.commit();
        return usersWeMet;
    }

    public void addNewMessage(Message message, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist( message );
        transaction.commit();
        em.close();
    }

    public List<Message> getMessages(int threadId, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createNamedQuery( "message.getMessages", Message.class );
        query.setParameter( "id", threadId );
        List messages = query.getResultList();
        transaction.commit();
        em.close();
        return messages;
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

}
