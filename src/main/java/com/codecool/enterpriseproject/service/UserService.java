package com.codecool.enterpriseproject.service;

import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.Gender;
import com.codecool.enterpriseproject.model.Personality;
import com.codecool.enterpriseproject.model.User;
import com.codecool.enterpriseproject.repository.UserRepository;
import com.codecool.enterpriseproject.util.MatchFinderUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserService extends BaseService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    MatchFinderUtil matchFinderUtil;

    @Autowired
    ChatBoxService chatBoxService;


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
        List<User> matches = userRepository.findUsersByAgeGreaterThanEqualAndAgeLessThanEqualAndGenderAndPartnerGenderAndPersonalityTypeAndInConversationFalse(minPartnerAge, maxPartnerAge, gender, partnerGender, optPartnerPersType);
        List<ChatBox> chatBoxes = chatBoxService.findPastChatBoxes(user);
        return matchFinderUtil.findTheOne(matches, chatBoxes);
    }




    public User getUserById(int id, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createNamedQuery("user.getUserById", User.class);
        query.setParameter("id", id);
        List user = query.getResultList();
        Object obj = null;
        if (!user.isEmpty()) {
            obj = user.get(0);
        }
        transaction.commit();
        em.close();
        return (User) obj;
    }


    public void setInConversation(User user, boolean bool, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        User mergedUser = em.merge(user);
        transaction.begin();
        System.out.println(user.getFirstName() + ": " + bool);
        mergedUser.setInConversation(bool);
        transaction.commit();
        em.close();
    }
}
