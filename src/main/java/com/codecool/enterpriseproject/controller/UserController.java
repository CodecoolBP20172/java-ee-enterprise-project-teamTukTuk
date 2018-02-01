package com.codecool.enterpriseproject.controller;

import com.codecool.enterpriseproject.dbhandler.UserDbHandler;
import com.codecool.enterpriseproject.model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.halt;

public class UserController {

    public static ModelAndView renderRegisterPage(Request req, Response res) {
        Map params = new HashMap<>();

        return new ModelAndView( params, "/index" );
    }

    public static ModelAndView renderPersonalityTest(Request req, Response res) {
        Map params = new HashMap<>();

        return new ModelAndView( params, "/personality" );
    }

    public static void checkIfInSession(Request request, Response response) {
        if (request.session().attribute( "id" ) == null) {
            response.redirect( "/" );
            halt( "Unauthorized access" );
        }
    }

    public static String registeringWithValidate(Request request, Response response, UserDbHandler dbHandler, EntityManager em) {
        if (request.queryParams( "password" ).equals( request.queryParams( "password_again" ) )) {
            User user = new User( request.queryParams( "first_name" ), request.queryParams( "last_name" ), Integer.parseInt( request.queryParams( "age" ) ), request.queryParams( "password" ), request.queryParams( "email" ), false, request.queryParams( "gender" ), request.queryParams( "preference" ) );
            System.out.println( request.queryParams( "password" ) );
            dbHandler.add( user, em );
            request.session().attribute( "id", user.getId() );
            response.redirect( "/user/page" );
        } else {
            return "rossz pw";
        }

        return "";
    }

    public static String loginWithValidate(Request request, Response response, UserDbHandler dbHandler, EntityManager em) {
        String userEmail = request.queryParams( "email" );
        String pswd = request.queryParams( "password" );
        User user = dbHandler.findUserByUserName( em, userEmail );
        if (user != null) {
            if (pswd.equals( user.getPassWord() )) {
                request.session().attribute( "id", user.getId() );
                response.redirect( "/personality_test" );
            }
            return "rosszpw";
        }
        return "";
    }


    public static Object analyzeForm(Request req, Response res, EntityManager em, UserDbHandler dbHandler) {
        //TODO validate input

        //TODO analise the result and set personality
        //personality is found here, but need to set it for the user
        User user = em.find( User.class, req.session().id() );
        int personalityType = findPersonality( req );
        dbHandler.updateUserPersonality(user, em, personalityType );
        System.out.println( "personality type of the user: " + personalityType );

        //TODO popup thx for filling the form

        //redirect to front page
        res.redirect( "/" );
        return null;
    }

    private static int findPersonality(Request req) {
        List<Integer> answers = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            answers.add( Integer.parseInt( req.queryParams( "q" + i ) ) );
        }
        return mostFrequent( answers );
    }

    private static Integer mostFrequent(List<Integer> list) {
        Map<Integer, Integer> counterMap = new HashMap<>();
        Integer maxValue = 0;
        Integer mostFrequentValue = null;

        for (Integer valueAsKey : list) {
            Integer counter = counterMap.get( valueAsKey );
            counterMap.put( valueAsKey, counter == null ? 1 : counter + 1 );
            counter = counterMap.get( valueAsKey );
            if (counter > maxValue) {
                maxValue = counter;
                mostFrequentValue = valueAsKey;
            }
        }
        return mostFrequentValue;
    }

}
