package com.codecool.enterpriseproject.dbhandler;

import com.codecool.enterpriseproject.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DbHandler {


    public void add(Object object, EntityManager em) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist( object );
        transaction.commit();

    }

    //TODO make this method dynamic
    public void updateUser(User user, EntityManager em) {
        User mergedUser = em.merge( user );
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        mergedUser.setFirstName( "ok" );
        transaction.commit();
    }


}
