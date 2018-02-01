package com.codecool.enterpriseproject.dbhandler;

import com.codecool.enterpriseproject.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

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

    public User findUserByUserName(EntityManager em, String email ) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createNamedQuery( "user.getUserByEmail", User.class );
        query.setParameter( "email", email );
        List user = query.getResultList();
        Object obj = null;
        if(!user.isEmpty()){
            obj = user.get(0);
        }
        transaction.commit();
        return (User) obj;
    }


}
