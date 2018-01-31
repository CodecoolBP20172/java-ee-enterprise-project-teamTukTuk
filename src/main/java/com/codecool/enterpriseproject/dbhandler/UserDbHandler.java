package com.codecool.enterpriseproject.dbhandler;

import com.codecool.enterpriseproject.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class UserDbHandler {


    public void addUser(User user, EntityManager em) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist( user );
        transaction.commit();

    }

    public void updateUser(User user, EntityManager em) {
        User mergedUser = em.merge( user );
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        mergedUser.setFirstName( "ok" );
        transaction.commit();
    }


}
