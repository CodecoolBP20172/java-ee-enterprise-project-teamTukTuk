package com.codecool.enterpriseproject;

import com.codecool.enterpriseproject.controller.UserController;
import com.codecool.enterpriseproject.dbhandler.UserDbHandler;
import com.codecool.enterpriseproject.model.Personality;
import com.codecool.enterpriseproject.model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.HashMap;

import static spark.Spark.*;


public class Main {

    public static void main(String[] args) {
        staticFileLocation("/public");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("enterprisePU");
        EntityManager em = emf.createEntityManager();

        UserDbHandler dbHandler = new UserDbHandler();
        populateDb(dbHandler, em);
      
      
        before( "/", (request, response) -> {
            response.redirect( "/user/page" );
        } );

        before( "/user/*", (request, response) -> {
            if(request.session().attribute("id") == null){
                response.redirect( "/register" );
                halt("Unauthorized access");
            }
        } );

        get( "/", (Request req, Response res) -> {

            return "hello";
        } );

        get("/user/page", (request, response) -> "testpage");

        // Always add generic routes to the end
        get( "/register", (Request req, Response res) -> new ThymeleafTemplateEngine().render( UserController.renderRegisterPage( req, res ) ) );

        post("/register_user", (request, response) -> {
            UserDbHandler dbHandler = new UserDbHandler();
            if (request.queryParams( "password" ).equals( request.queryParams( "password_again" ) )) {
                User user = new User( request.queryParams( "first_name" ), request.queryParams( "last_name" ),Integer.parseInt( request.queryParams( "age" ) ) , request.queryParams( "password" ), request.queryParams( "email" ), false );
                System.out.println(request.queryParams( "password" ));
                dbHandler.addUser( user, em );
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
            UserDbHandler dbHandler = new UserDbHandler();
            User user = dbHandler.findUserByUserName( em, userEmail );
            if (user != null) {
                if (pswd.equals( user.getPassWord() )) {
                    request.session().attribute( "id", user.getId() );
                    response.redirect( "/" );
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


    private static void populateDb(UserDbHandler dbHandler, EntityManager em) {
        dbHandler.addUser( new User("John", "Johnson", 37, "pass", 1, "Male", "Female"), em );
        dbHandler.addUser( new User("Maria", "Johnes", 28, "pass", 2, "Female", "Male"), em );
        dbHandler.addUser( new User("Eduardo", "Silva", 48, "pass", 3, "Male", "Female"), em );
        dbHandler.addUser( new User("Jane", "Jacobs", 32, "pass", 8, "Female", "Male"), em );
        dbHandler.addUser( new User("Gupta", "Aditi", 40, "pass", 9, "Male", "Female"), em );
    }
}
