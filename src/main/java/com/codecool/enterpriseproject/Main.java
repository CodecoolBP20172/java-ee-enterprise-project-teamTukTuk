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


        // Always add generic routes to the end
        get( "/register", (Request req, Response res) -> {
            UserDbHandler dbHandler = new UserDbHandler();
            User user = em.find( User.class, 1 );
            dbHandler.updateUser( user, em );
            return new ThymeleafTemplateEngine().render( UserController.renderRegisterPage( req, res ) );
        } );

        // should always be the last route
        get( "/", (Request req, Response res) -> {
            HashMap params = new HashMap();
            return new ThymeleafTemplateEngine().render(new ModelAndView(params, "index"));
        } );


    }
}
