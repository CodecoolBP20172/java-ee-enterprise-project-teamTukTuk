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
            User user = new User( request.queryParams( "first_name" ), request.queryParams( "last_name" ),Integer.parseInt( request.queryParams( "age" ) ) , request.queryParams( "password" ), request.queryParams( "user_email" ), false );
            System.out.println(request.queryParams( "password" ));
            dbHandler.addUser( user, em );
            request.session().attribute( "id", user.getId() );
            response.redirect( "/" );
            return "";
        });


    }
}
