package com.codecool.enterpriseproject;

import com.codecool.enterpriseproject.controller.ChatController;
import com.codecool.enterpriseproject.controller.UserController;
import com.codecool.enterpriseproject.dbhandler.ChatBoxDbHandler;
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

        UserDbHandler dbHandler = new UserDbHandler();
        ChatBoxDbHandler chatBoxDbHandler = new ChatBoxDbHandler();

        before( "/user/*", UserController::checkIfInSession );

        get( "/user/page", (request, response) -> new ThymeleafTemplateEngine().render(UserController.renderUserPage(request, response, dbHandler, emf)) );

        get( "/testChat", (Request req, Response res) -> ChatController.renderChatPage( req, res, dbHandler, chatBoxDbHandler, emf ) );

        get( "/", (Request req, Response res) -> new ThymeleafTemplateEngine().render( UserController.renderRegisterPage( req, res ) ) );

        post( "/register_user", (Request request, Response response) -> UserController.registeringWithValidate( request, response, dbHandler, emf ) );

        post( "/login", (request, response) -> UserController.loginWithValidate( request, response, dbHandler, emf ) );

        //need to check first if signed in, otherwise should be 404 -Attila
        get( "/personality_test", (Request req, Response res) -> new ThymeleafTemplateEngine().render( UserController.renderPersonalityTest( req, res ) ) );

        post( "/set_personality", (Request req, Response res) ->
                new ThymeleafTemplateEngine().render( UserController.analyzeForm( req, res , emf, dbHandler) ) );

        get("/dashboard", (Request req, Response res) -> new ThymeleafTemplateEngine().render(ChatController.renderChatPage(req, res, dbHandler, chatBoxDbHandler, emf)));

        post("post_message", (Request request, Response response) -> {
            return new ThymeleafTemplateEngine().render(ChatController.writeMessageIntoDB(request, response, dbHandler, chatBoxDbHandler, emf));
        });

        get("/logout", (request, response) -> {
            request.session().invalidate();
            response.redirect("/");
            return "";
        });
    }
}

