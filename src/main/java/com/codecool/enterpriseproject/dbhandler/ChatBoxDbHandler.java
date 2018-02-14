package com.codecool.enterpriseproject.dbhandler;

import com.codecool.enterpriseproject.model.ChatBox;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class ChatBoxDbHandler {
    List findUsersWeMet(EntityManager em, String email) {
        System.out.println("here");
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        //here appears but the there doesn't. why?
        Query query = em.createNamedQuery( "chatBox.getUsersWeMet", ChatBox.class );
        query.setParameter( "email", email );
        List usersWeMet = query.getResultList();
        transaction.commit();
        System.out.println("there");
        return usersWeMet;
    }
}
