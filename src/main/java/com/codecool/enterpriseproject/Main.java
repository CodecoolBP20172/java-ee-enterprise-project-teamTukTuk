package com.codecool.enterpriseproject;

import com.codecool.enterpriseproject.controller.UserController;
import com.codecool.enterpriseproject.dbhandler.UserDbHandler;
import com.codecool.enterpriseproject.model.User;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static spark.Spark.*;


public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("enterprisePU");
        EntityManager em = emf.createEntityManager();

        get( "/", (Request req, Response res) -> {
            UserDbHandler dbHandler = new UserDbHandler();
            User user = new User("anyad", "apad", 37, "titkoskód");
            dbHandler.addUser( user, em );

            return "hello";
        } );

        // Always add generic routes to the end
        get( "/register", (Request req, Response res) -> {
            UserDbHandler dbHandler = new UserDbHandler();
            User user = em.find( User.class, 1 );
            dbHandler.updateUser( user, em );
            return new ThymeleafTemplateEngine().render( UserController.renderRegisterPage( req, res ) );
        } );


    }
}
