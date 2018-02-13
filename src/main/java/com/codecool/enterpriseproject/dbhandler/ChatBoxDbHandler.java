package com.codecool.enterpriseproject.dbhandler;

import com.codecool.enterpriseproject.model.ChatBox;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class ChatBoxDbHandler {
    public List findChatBoxByEmail(EntityManager em, String email) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createNamedQuery( "chatBox.getChatBoxByEmail", ChatBox.class );
        query.setParameter( "email", email );
        List chatBoxes = query.getResultList();
        transaction.commit();
        return chatBoxes;
    }
}
