package com.codecool.dbhandler;

import com.codecool.model.User;
import org.hibernate.criterion.CriteriaQuery;

import javax.persistence.*;
import java.util.List;

public class UserDbHandler {

    public User findUser(int id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("enterprisePU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        User user = (User)em.createQuery( "Select u From users u WHERE u.id LIKE :userId" )
                .setParameter( "userId", id ).getSingleResult();
        transaction.commit();
        emf.close();
        em.close();
        return user;

    }

    public void addUser(User user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("enterprisePU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist( user );
        transaction.commit();

        em.close();
        emf.close();
    }

    public void updateUser(User user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("enterprisePU");
        EntityManager em = emf.createEntityManager();

        User mergedUser = em.merge( user );
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        mergedUser.setFirstName("ok");

        transaction.commit();

        em.close();
        emf.close();

    }

    public User runSingleResultQuery(CriteriaQuery cq) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("enterprisejpa");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Query query = em.createQuery( String.valueOf( cq ) );
        User user = (User) query.getSingleResult();
        transaction.commit();

        em.close();
        emf.close();

        return user;
    }

    public List<User> runResultListQuery(CriteriaQuery criteriaQuery) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("enterprisejpa");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Query query = em.createQuery( String.valueOf( criteriaQuery ) );
        List<User> resultList = query.getResultList();
        transaction.commit();

        em.close();
        emf.close();
        return resultList;
    }

}
