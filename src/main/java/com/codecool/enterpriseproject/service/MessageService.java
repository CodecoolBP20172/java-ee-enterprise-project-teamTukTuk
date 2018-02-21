package com.codecool.enterpriseproject.service;

import com.codecool.enterpriseproject.model.Message;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class MessageService extends BaseService {


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
}
