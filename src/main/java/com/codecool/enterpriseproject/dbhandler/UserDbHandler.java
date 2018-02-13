package com.codecool.enterpriseproject.dbhandler;

import com.codecool.enterpriseproject.model.Gender;
import com.codecool.enterpriseproject.model.Personality;
import com.codecool.enterpriseproject.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class UserDbHandler {


    public void add(Object object, EntityManager em) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist( object );
        transaction.commit();
    }

    //TODO make this method dynamic
    public void updateUserPersonality(User user, EntityManager em, int personality) {
        User mergedUser = em.merge( user );
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        mergedUser.setPersonalityType(personality);
        mergedUser.setOptPartnerPersType(personality);
        transaction.commit();
    }

    public User findUserByEmail(EntityManager em, String email) {
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
        return (User) obj;
    }

    public User findUserByPersonality(EntityManager em, Personality pers) {
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
        return (User) obj;
    }

    public User findMatch(EntityManager em, User user) {
        //TODO
        //find a match with max 5(?) years difference,
        int maxDifference = 5;
        int minPartnerAge = user.getAge() - maxDifference;
        int maxPartnerAge = user.getAge() + maxDifference;
        //with the right gender and orientation
        Gender gender = user.getGender();
        Gender partnerGender = user.getPartnerGender();
        //with the right personality
        Personality optPartnerPersType = user.getOptPartnerPersType();
        //where the partner is not in a conversation
        //then return the first match
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createNamedQuery( "user.findMatch", User.class );
        query.setParameter( "minPartnerAge", minPartnerAge );
        query.setParameter( "maxPartnerAge", maxPartnerAge );
        query.setParameter( "gender", gender );
        query.setParameter( "partnerGender", partnerGender );
        query.setParameter( "optPartnerPersType", optPartnerPersType );
        List match = query.getResultList();
        Object obj = null;

        /*right now it just returns the first from the list
        *but later we have to make sure the user is not getting the same match
        *maybe we could give a list to the user where the previous match id-s are listed
        *so that we can avoid them (in the user.findMatch @NamedQuery)*/
        if (!match.isEmpty()) {
            obj = match.get( 0 );
        }
        transaction.commit();

        return (User) obj;
    }
}
