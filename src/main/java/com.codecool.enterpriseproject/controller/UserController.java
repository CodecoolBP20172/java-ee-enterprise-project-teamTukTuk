package com.codecool.enterpriseproject.controller;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserController {
    public static ModelAndView renderHomePage(Request req, Response res) {
        Map params = new HashMap<>();

        return new ModelAndView(params, "/index");
    }
}
