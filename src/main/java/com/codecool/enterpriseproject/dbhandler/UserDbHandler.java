package com.codecool.enterpriseproject.dbhandler;

import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.Gender;
import com.codecool.enterpriseproject.model.Personality;
import com.codecool.enterpriseproject.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.xml.stream.FactoryConfigurationError;
import java.util.ArrayList;
import java.util.List;

public class UserDbHandler {

    private ChatBoxDbHandler chatBoxDbHandler;

    public UserDbHandler(ChatBoxDbHandler chatBoxDbHandler) {
        this.chatBoxDbHandler = chatBoxDbHandler;
    }

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
        //find a match with max 5(?) years difference
        int maxDifference = 5;
        int minPartnerAge = user.getAge() - maxDifference;
        int maxPartnerAge = user.getAge() + maxDifference;
        Gender gender = user.getGender();
        Gender partnerGender = user.getPartnerGender();
        Personality optPartnerPersType = user.getOptPartnerPersType();
        String email = user.getEmail();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createNamedQuery( "user.findMatch", User.class );
        query.setParameter( "minPartnerAge", minPartnerAge );
        query.setParameter( "maxPartnerAge", maxPartnerAge );
        query.setParameter( "gender", gender );
        query.setParameter( "partnerGender", partnerGender );
        query.setParameter( "optPartnerPersType", optPartnerPersType );
        List matches = query.getResultList();

        System.out.println("1st from matches: " + matches.get(0));

        Object obj = null;

        List usersWeMet = chatBoxDbHandler.findUsersWeMet(em, email);
        if (usersWeMet.isEmpty()) {
            System.out.println("empty");
        } else {
            System.out.println("usersWeMet: " + usersWeMet);
        }

        if (!matches.isEmpty() && !usersWeMet.isEmpty()) {
            boolean matchFound = false;
            while (!matchFound) {
                for (Object match : matches) {
                    if (!usersWeMet.contains(match)) {
                        obj = match;
                        matchFound = true;
                    }
                }
            }
        } else {
            obj = matches.get(0);
        }
        transaction.commit();

        return (User) obj;
    }
}
