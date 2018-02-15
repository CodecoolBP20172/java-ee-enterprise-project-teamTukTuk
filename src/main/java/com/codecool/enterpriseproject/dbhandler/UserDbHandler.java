package com.codecool.enterpriseproject.dbhandler;

import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.Gender;
import com.codecool.enterpriseproject.model.Personality;
import com.codecool.enterpriseproject.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDbHandler {

    private ChatBoxDbHandler chatBoxDbHandler;

    public UserDbHandler(ChatBoxDbHandler chatBoxDbHandler) {
        this.chatBoxDbHandler = chatBoxDbHandler;
    }

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

    public User findUserByEmail(EntityManagerFactory emf, String email) {
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

    public User findMatch(EntityManagerFactory emf, User user) {
        //find a match with max 5(?) years difference
        EntityManager em = emf.createEntityManager();
        int maxDifference = 5;
        int minPartnerAge = user.getAge() - maxDifference;
        int maxPartnerAge = user.getAge() + maxDifference;
        Gender gender = user.getGender();
        Gender partnerGender = user.getPartnerGender();
        Personality optPartnerPersType = user.getOptPartnerPersType();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createNamedQuery( "user.findMatch", User.class );
        query.setParameter( "minPartnerAge", minPartnerAge );
        query.setParameter( "maxPartnerAge", maxPartnerAge );
        query.setParameter( "gender", gender );
        query.setParameter( "partnerGender", partnerGender );
        query.setParameter( "optPartnerPersType", optPartnerPersType );
        List matches = query.getResultList();
        transaction.commit();
        Object obj = findTheOne(em, matches, user);
        em.close();
        return (User) obj;
    }

    private Object findTheOne(EntityManager em, List matches, User user) {
        Object theOne = null;
        List pastChatboxes = chatBoxDbHandler.findPastChatBoxes(em, user);

        //these should be tested if you can convert Objects to ChatBoxes
        //and then to a List of Users like this
        ArrayList<ChatBox> chatboxes = setPastChatBoxes(pastChatboxes);
        List usersMet = findUsersMet(chatboxes);

        System.out.println("usersmet: " + usersMet);

        if (matches.isEmpty()) {return null;}
        boolean matchFound = false;
        while (!matchFound) {
            int lengthChecker = 0;
            System.out.println("matches size: " + matches.size());
            for (Object match : matches) {
                lengthChecker += 1;
                System.out.println("lenghtCh: " + lengthChecker);
                if (!usersMet.contains(match)) {
                    theOne = match;
                    matchFound = true;
                }
                if (lengthChecker>=matches.size() && !matchFound) {
                    //if the user have talked to all matches we return null
                    return null;
                }
            }
        }
        return (User) theOne;
    }

    private List findUsersMet(ArrayList<ChatBox> chatboxes) {
        List usersMet = new ArrayList();
        for (ChatBox chatBox : chatboxes) {
            usersMet.add(chatBox.getSecondUser());
        }
        return usersMet;
    }

    private ArrayList<ChatBox> setPastChatBoxes(List pastChatboxes) {
        ArrayList<ChatBox> chatboxes = new ArrayList<>();
        for (Object chatbox : pastChatboxes) {
            chatboxes.add((ChatBox) chatbox);
        }
        return chatboxes;
    }

    public User getUserById(int id, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createNamedQuery( "user.getUserById", User.class );
        query.setParameter( "id", id );
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
