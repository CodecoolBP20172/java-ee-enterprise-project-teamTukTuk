package com.codecool.enterpriseproject;

import com.codecool.enterpriseproject.controller.UserController;
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
        staticFileLocation("/public");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("enterprisePU");
        EntityManager em = emf.createEntityManager();

        UserDbHandler dbHandler = new UserDbHandler();
        
        before( "/", (request, response) -> {
            response.redirect( "/user/page" );
        } );

        before( "/user/*", (request, response) -> {
            if(request.session().attribute("id") == null){
                response.redirect( "/register" );
                halt("Unauthorized access");
            }
        } );


        get("/user/page", (request, response) -> "testpage");


        get("/testChat", (Request req, Response res) -> {
            User sanyika = new User("sanyika", "abarótistartvből", "sanyika@email.com", 17,"pass", 1, "Male", "Female");
            User jolika = new User("jolika", "sanyiszerelme", "jolika@email.com", 16,"pass", 3, "Female", "Male");
            dbHandler.add(sanyika, em);
            dbHandler.add(jolika, em);
            ChatBox chatBox = new ChatBox(sanyika, jolika);
            Message sanyiÜzenete = new Message(chatBox, new Date(), "ez a message", sanyika);
            dbHandler.add(chatBox, em);
            dbHandler.add(sanyiÜzenete, em);
            return "siker";
        });

        get( "/register", (Request req, Response res) -> new ThymeleafTemplateEngine().render( UserController.renderRegisterPage( req, res ) ) );

        post("/register_user", (request, response) -> {
            if (request.queryParams( "password" ).equals( request.queryParams( "password_again" ) )) {
                User user = new User( request.queryParams( "first_name" ), request.queryParams( "last_name" ),Integer.parseInt( request.queryParams( "age" ) ) , request.queryParams( "password" ), request.queryParams( "email" ), false, request.queryParams( "gender" ), request.queryParams( "preference" ) );
                System.out.println(request.queryParams( "password" ));
                dbHandler.add( user, em );
                request.session().attribute( "id", user.getId() );
                response.redirect( "/" );
            } else {
                return "rossz pw";
            }

            return "";
        });

        post("/login", (request, response) -> {
            String userEmail = request.queryParams( "email" );
            String pswd = request.queryParams( "password" );
            User user = dbHandler.findUserByUserName( em, userEmail );
            if (user != null) {
                if (pswd.equals( user.getPassWord() )) {
                    request.session().attribute( "id", user.getId() );
                    response.redirect( "/personality_test" );
                }
                return "kaki";
            }
            return "";
        });


        // Always add generic routes to the end
  

        //need to check first if signed in, otherwise should be 404 -Attila
        get( "/personality_test", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render( UserController.renderPersonalityTest( req, res ) );
        } );

        post("/set_personality", (Request req, Response res) ->
                new ThymeleafTemplateEngine().render(UserController.analyzeForm(req, res)));

    }
}
