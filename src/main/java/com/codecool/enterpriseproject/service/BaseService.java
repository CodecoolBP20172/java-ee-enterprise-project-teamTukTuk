package com.codecool.enterpriseproject.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class BaseService {

    public void addObject(Object object, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist( object );
        transaction.commit();
        em.close();
    }

}
