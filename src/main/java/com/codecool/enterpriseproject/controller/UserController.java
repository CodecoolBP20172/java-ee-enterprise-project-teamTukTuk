package com.codecool.enterpriseproject.controller;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserController {

    public static ModelAndView renderRegisterPage(Request req, Response res) {
        Map params = new HashMap<>();

        return new ModelAndView(params, "/registration");
    }
}
