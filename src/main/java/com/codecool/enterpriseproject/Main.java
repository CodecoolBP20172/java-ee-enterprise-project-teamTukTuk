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

        before( "/user/*", UserController::checkIfInSession );

        get( "/user/page", (request, response) -> new ThymeleafTemplateEngine().render(UserController.renderUserPage(request, response, dbHandler, em)) );

        get( "/testChat", (Request req, Response res) -> ChatController.renderTestChat( dbHandler, em ) );

        get( "/", (Request req, Response res) -> new ThymeleafTemplateEngine().render( UserController.renderRegisterPage( req, res ) ) );

        post( "/api/register", (Request request, Response response) -> UserController.handleRegisterInput( request, response, dbHandler, em ) );

        post( "/login", (request, response) -> UserController.loginWithValidate( request, response, dbHandler, em ) );

        //need to check first if signed in, otherwise should be 404 -Attila
        get( "/personality_test", (Request req, Response res) -> new ThymeleafTemplateEngine().render( UserController.renderPersonalityTest( req, res ) ) );

        post( "/set_personality", (Request req, Response res) ->
                new ThymeleafTemplateEngine().render( UserController.analyzeForm( req, res , em, dbHandler) ) );
    }
}

