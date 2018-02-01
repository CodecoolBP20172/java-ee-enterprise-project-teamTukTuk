package com.codecool.enterpriseproject;

import com.codecool.enterpriseproject.controller.UserController;
import com.codecool.enterpriseproject.dbhandler.DbHandler;
import com.codecool.enterpriseproject.model.Message;
import com.codecool.enterpriseproject.model.User;
import com.codecool.enterpriseproject.model.ChatBox;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Date;

import static spark.Spark.*;


public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("enterprisePU");
        EntityManager em = emf.createEntityManager();

        get( "/", (Request req, Response res) -> {
            DbHandler dbHandler = new DbHandler();
            User user = new User("anyad", "apad", 37, "titkoskód", false, "male", "female", "personality");
            dbHandler.add( user, em );

            return "hello";
        } );

        // Always add generic routes to the end
        get( "/register", (Request req, Response res) -> {
            DbHandler dbHandler = new DbHandler();
            User user = em.find( User.class, 1 );
            dbHandler.updateUser( user, em );
            return new ThymeleafTemplateEngine().render( UserController.renderRegisterPage( req, res ) );
        } );

        get("/testChat", (Request req, Response res) -> {
            DbHandler dbHandler = new DbHandler();
            User sanyika = new User("sanyika", "abarótistartvből", 19, "gyererámpénz",false, "male", "female", "personality");
            User jolika = new User("jolika", "sanyiszerelme", 17, "adjálpénztsanyi",false, "female", "male", "personality");
            dbHandler.add(sanyika, em);
            dbHandler.add(jolika, em);
            ChatBox chatBox = new ChatBox(sanyika, jolika);
            Message sanyiÜzenete = new Message(chatBox, new Date(), "ez a message", sanyika);
            dbHandler.add(chatBox, em);
            dbHandler.add(sanyiÜzenete, em);
            return "siker";
        });


    }

}
