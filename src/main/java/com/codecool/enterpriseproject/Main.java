package com.codecool.enterpriseproject;

import com.codecool.enterpriseproject.controller.UserController;
import com.codecool.enterpriseproject.dbhandler.MessageDbHandler;
import com.codecool.enterpriseproject.dbhandler.ThreadDbHandler;
import com.codecool.enterpriseproject.dbhandler.UserDbHandler;
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

        get("/testChat", (Request req, Response res) -> {
            UserDbHandler userDbHandler = new UserDbHandler();
            ThreadDbHandler threadHandler = new ThreadDbHandler();
            MessageDbHandler messageHandler = new MessageDbHandler();
            User sanyika = new User("sanyika", "abarótistartvből", 19, "gyererámpénz");
            User jolika = new User("jolika", "sanyiszerelme", 17, "adjálpénztsanyi");
            userDbHandler.addUser(sanyika, em);
            userDbHandler.addUser(jolika, em);
            ChatBox chatBox = new ChatBox(sanyika, jolika);
            Message sanyiÜzenete = new Message(chatBox, new Date(), "ez a message", sanyika);
            threadHandler.addThread(chatBox, em);
            messageHandler.addMessage(sanyiÜzenete, em);

            return "siker";
        });


    }

}
