package com.codecool.enterpriseproject.dbhandler;

import com.codecool.enterpriseproject.model.ChatBox;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ThreadDbHandler {

    public void addThread(ChatBox thread, EntityManager em) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist( thread );
        transaction.commit();
    }

}
