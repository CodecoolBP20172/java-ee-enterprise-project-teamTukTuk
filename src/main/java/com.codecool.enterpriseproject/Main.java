package com.codecool.enterpriseproject;

import com.codecool.enterpriseproject.controller.UserController;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;


public class Main {

    public static void main(String[] args) {

        Spark.get("/", (req, res) -> "Hello World");

        // Always add generic routes to the end
        Spark.get("/register", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render( UserController.renderRegisterPage(req, res) );
        });



    }
}
