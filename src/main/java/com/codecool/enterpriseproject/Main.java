package com.codecool.enterpriseproject;

import com.codecool.enterpriseproject.controller.ChatController;
import com.codecool.enterpriseproject.controller.UserController;
import com.codecool.enterpriseproject.service.ChatBoxService;
import com.codecool.enterpriseproject.service.MessageService;
import com.codecool.enterpriseproject.service.UserService;
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

        ChatBoxService chatBoxService = new ChatBoxService();
        UserService userService = new UserService(chatBoxService);
        MessageService messageService = new MessageService();

        before( "/user/*", UserController::checkIfInSession );

        get( "/user/page", (request, response) -> new ThymeleafTemplateEngine().render(UserController.renderUserPage(request, response, chatBoxService, userService, emf)) );

        get( "/testChat", (Request req, Response res) -> ChatController.renderChatPage( req, res, userService, chatBoxService, emf, messageService ) );

        get( "/", (Request req, Response res) -> new ThymeleafTemplateEngine().render( UserController.renderRegisterPage( req, res ) ) );

        post( "/api/register", (Request request, Response response) -> UserController.handleRegisterInput( request, response, userService, emf ), json() );

        post( "/api/login", (request, response) -> UserController.loginWithValidate( request, response, userService, emf ) );

        //need to check first if signed in, otherwise should be 404 -Attila
        get( "/personality_test", (Request req, Response res) -> new ThymeleafTemplateEngine().render( UserController.renderPersonalityTest( req, res ) ) );

        post( "/set_personality", (Request req, Response res) ->
                new ThymeleafTemplateEngine().render( UserController.analyzeForm( req, res , emf, userService) ) );

        get("/dashboard", (Request req, Response res) -> new ThymeleafTemplateEngine().render(ChatController.renderChatPage(req, res, userService, chatBoxService, emf, messageService)));

        post("post_message", (Request request, Response response) -> new ThymeleafTemplateEngine().render(ChatController.writeMessageIntoDB(request, response, userService, chatBoxService, emf, messageService)) );

        get("/logout", (request, response) -> {
            request.session().invalidate();
            response.redirect("/");
            return "";
        });

        post("/doyoulikeme", (request, response) -> new ThymeleafTemplateEngine().render(ChatController.getNewPartner(request, response, chatBoxService, emf, userService)));
    }
}

