package com.codecool.enterpriseproject;

import com.codecool.enterpriseproject.controller.UserController;
import com.codecool.enterpriseproject.dbhandler.UserDbHandler;
import com.codecool.enterpriseproject.model.User;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;


public class Main {

    public static void main(String[] args) {

        Spark.get("/", (req, res) -> {
            UserDbHandler dbHandler = new UserDbHandler();
            User user = new User( "asadds", "dafafda", 56, "titkos" );
            dbHandler.addUser( user );

            return "Hello Túrósfasz";
        });

        // Always add generic routes to the end
        Spark.get("/register", (Request req, Response res) -> {
            UserDbHandler dbHandler = new UserDbHandler();
            User user = dbHandler.findUser( 1 );
            dbHandler.updateUser(user);
            return new ThymeleafTemplateEngine().render( UserController.renderRegisterPage(req, res) );
        });



    }
}
