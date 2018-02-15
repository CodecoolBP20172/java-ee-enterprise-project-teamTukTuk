package com.codecool.enterpriseproject;

import com.codecool.enterpriseproject.controller.ChatController;
import com.codecool.enterpriseproject.controller.UserController;
import com.codecool.enterpriseproject.dbhandler.ChatBoxDbHandler;
import com.codecool.enterpriseproject.dbhandler.UserDbHandler;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static com.codecool.enterpriseproject.util.JsonUtil.json;
import static spark.Spark.*;


public class Main {

    public static void main(String[] args) {
        staticFileLocation( "/public" );
        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "enterprisePU" );

        ChatBoxDbHandler chatBoxDbHandler = new ChatBoxDbHandler();
        UserDbHandler dbHandler = new UserDbHandler(chatBoxDbHandler);

        before( "/user/*", UserController::checkIfInSession );

        get( "/user/page", (request, response) -> new ThymeleafTemplateEngine().render(UserController.renderUserPage(request, response, chatBoxDbHandler, dbHandler, emf)) );

        get( "/testChat", (Request req, Response res) -> ChatController.renderChatPage( req, res, dbHandler, chatBoxDbHandler, emf ) );

        get( "/", (Request req, Response res) -> new ThymeleafTemplateEngine().render( UserController.renderRegisterPage( req, res ) ) );

        post( "/api/register", (Request request, Response response) -> UserController.handleRegisterInput( request, response, dbHandler, emf ), json() );

        post( "/api/login", (request, response) -> UserController.loginWithValidate( request, response, dbHandler, emf ) );

        //need to check first if signed in, otherwise should be 404 -Attila
        get( "/personality_test", (Request req, Response res) -> new ThymeleafTemplateEngine().render( UserController.renderPersonalityTest( req, res ) ) );

        post( "/set_personality", (Request req, Response res) ->
                new ThymeleafTemplateEngine().render( UserController.analyzeForm( req, res , emf, dbHandler) ) );

        get("/dashboard", (Request req, Response res) -> new ThymeleafTemplateEngine().render(ChatController.renderChatPage(req, res, dbHandler, chatBoxDbHandler, emf)));

        post("post_message", (Request request, Response response) -> new ThymeleafTemplateEngine().render(ChatController.writeMessageIntoDB(request, response, dbHandler, chatBoxDbHandler, emf)) );

        get("/logout", (request, response) -> {
            request.session().invalidate();
            response.redirect("/");
            return "";
        });

        post("/doyoulikeme", (request, response) -> new ThymeleafTemplateEngine().render(ChatController.getNewPartner(request, response, chatBoxDbHandler, emf, dbHandler)));
    }
}

