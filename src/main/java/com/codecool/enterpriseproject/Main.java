package com.codecool.enterpriseproject;

import com.codecool.enterpriseproject.controller.ChatController;
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
        staticFileLocation( "/public" );
        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "enterprisePU" );
        EntityManager em = emf.createEntityManager();

        UserDbHandler dbHandler = new UserDbHandler();
        populateDb( dbHandler, em );


        before( "/user/*", UserController::checkIfInSession );

        get( "/user/page", (request, response) -> "testpage" );

        get( "/testChat", (Request req, Response res) -> ChatController.renderTestChat( dbHandler, em ) );

        get( "/", (Request req, Response res) -> new ThymeleafTemplateEngine().render( UserController.renderRegisterPage( req, res ) ) );

        post( "/register_user", (Request request, Response response) -> UserController.registeringWithValidate( request, response, dbHandler, em ) );

        post( "/login", (request, response) -> UserController.loginWithValidate( request, response, dbHandler, em ) );

        //need to check first if signed in, otherwise should be 404 -Attila
        get( "/personality_test", (Request req, Response res) -> new ThymeleafTemplateEngine().render( UserController.renderPersonalityTest( req, res ) ) );

        post( "/set_personality", (Request req, Response res) ->
                new ThymeleafTemplateEngine().render( UserController.analyzeForm( req, res ) ) );
    }

    private static void populateDb(UserDbHandler dbHandler, EntityManager em) {
        dbHandler.add( new User( "John", "Johnson", "email@gmail.com", 37, "pass", 1, "Male", "Female" ), em );
        dbHandler.add( new User( "Maria", "Johnes", "email2@gmail.com", 36, "pass", 2, "Female", "Male" ), em );
        dbHandler.add( new User( "Eduardo", "Silva", "email3@gmail.com", 48, "pass", 3, "Male", "Female" ), em );
        dbHandler.add( new User( "Jane", "Jacobs", "email4@gmail.com", 32, "pass", 8, "Female", "Male" ), em );
        dbHandler.add( new User( "Gupta", "Aditi", "email5@gmail.com", 40, "pass", 9, "Male", "Female" ), em );
    }

}
