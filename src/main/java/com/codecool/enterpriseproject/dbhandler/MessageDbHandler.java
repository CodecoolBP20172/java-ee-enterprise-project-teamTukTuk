package com.codecool.enterpriseproject.dbhandler;
import com.codecool.enterpriseproject.model.Message;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


public class MessageDbHandler {

    public void addMessage(Message message, EntityManager em) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist( message );
        transaction.commit();
    }


}
