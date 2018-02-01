package com.codecool.enterpriseproject;

import com.codecool.enterpriseproject.controller.UserController;
import com.codecool.enterpriseproject.dbhandler.UserDbHandler;
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

        // should always be the last route



    }
}
