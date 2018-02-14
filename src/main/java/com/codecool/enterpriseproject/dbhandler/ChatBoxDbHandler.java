package com.codecool.enterpriseproject.dbhandler;

import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class ChatBoxDbHandler {
    List findPastChatBoxes(EntityManager em, User user) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createNamedQuery( "chatBox.getUsersWeMet", ChatBox.class );
        query.setParameter( "user", user );
        List usersWeMet = query.getResultList();
        transaction.commit();
        return usersWeMet;
    }
}
