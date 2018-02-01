package com.codecool.enterpriseproject.controller;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {

    public static ModelAndView renderRegisterPage(Request req, Response res) {
        Map params = new HashMap<>();

        return new ModelAndView(params, "/registration");
    }

    public static ModelAndView renderPersonalityTest(Request req, Response res) {
        Map params = new HashMap<>();

        return new ModelAndView(params, "/personality");
    }

    public static Object analyzeForm(Request req, Response res) {
        //TODO validate input

        //TODO analise the result and set personality
        //personality is found here, but need to set it for the user
        int personalityType = findPersonality(req);
        System.out.println("personality type of the user: " + personalityType);

        //TODO popup thx for filling the form

        //redirect to front page
        res.redirect("/");
        return null;
    }

    private static int findPersonality(Request req) {
        List<Integer> answers = new ArrayList<>();
        for (int i=1; i<=5; i++) {
            answers.add(Integer.parseInt(req.queryParams("q" + i)));
        }
        return mostFrequent(answers);
    }

    private static Integer mostFrequent(List<Integer> list) {
        Map<Integer, Integer> counterMap = new HashMap<>();
        Integer maxValue = 0;
        Integer mostFrequentValue = null;

        for(Integer valueAsKey : list) {
            Integer counter = counterMap.get(valueAsKey);
            counterMap.put(valueAsKey, counter == null ? 1 : counter + 1);
            counter = counterMap.get(valueAsKey);
            if (counter > maxValue) {
                maxValue = counter;
                mostFrequentValue = valueAsKey;
            }
        }
        return mostFrequentValue;
    }

}
