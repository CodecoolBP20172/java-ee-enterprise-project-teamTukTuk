package com.codecool.enterpriseproject.dbhandler;

import com.codecool.enterpriseproject.model.Personality;
import com.codecool.enterpriseproject.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class UserDbHandler {


    public void add(User user, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist( user );
        transaction.commit();
        em.close();
    }

    //TODO make this method dynamic
    public void updateUserPersonality(User user, EntityManagerFactory emf, int personality) {
        EntityManager em = emf.createEntityManager();
        User mergedUser = em.merge( user );
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        mergedUser.setPersonalityType(personality);
        mergedUser.setOptPartnerPersType(personality);
        transaction.commit();
        em.close();
    }

    public User findUserByUserName(EntityManagerFactory emf, String email) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createNamedQuery( "user.getUserByEmail", User.class );
        query.setParameter( "email", email );
        List user = query.getResultList();
        Object obj = null;
        if (!user.isEmpty()) {
            obj = user.get( 0 );
        }
        transaction.commit();
        em.close();
        return (User) obj;
    }

    public User findUserByPersonality(EntityManagerFactory emf, Personality pers) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createNamedQuery( "user.getUserByPersonality", User.class );
        query.setParameter( "pers", pers );
        List user = query.getResultList();
        Object obj = null;
        if (!user.isEmpty()) {
            obj = user.get( 0 );
        }
        transaction.commit();
        em.close();
        return (User) obj;
    }
}
