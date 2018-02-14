package com.codecool.enterpriseproject.controller;

import com.codecool.enterpriseproject.dbhandler.UserDbHandler;
import com.codecool.enterpriseproject.model.Personality;
import com.codecool.enterpriseproject.model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
            response.redirect("/");
            halt("Unauthorized access");
        }
        response.redirect("/dashboard");
    }

    public static String registeringWithValidate(Request request, Response response, UserDbHandler dbHandler, EntityManagerFactory emf) {
        if (request.queryParams( "password" ).equals( request.queryParams( "password_again" ) )) {
            User user = new User( request.queryParams( "first_name" ), request.queryParams( "last_name" ), Integer.parseInt( request.queryParams( "age" ) ), request.queryParams( "password" ), request.queryParams( "email" ), false, request.queryParams( "gender" ), request.queryParams( "preference" ) );
            System.out.println( request.queryParams( "password" ) );
            dbHandler.add( user, emf );
            request.session(true);
            System.out.println(user.getId());
            request.session().attribute( "email", user.getEmail() );
            request.session().attribute("id", user.getId());
            System.out.println(request.session().attribute("email").toString());
            response.redirect( "/personality_test" );
        } else {
            return "rossz pw";
        }
        return "";
    }

    public static String loginWithValidate(Request request, Response response, UserDbHandler dbHandler, EntityManagerFactory emf) {
        String userEmail = request.queryParams( "email" );
        String pswd = request.queryParams( "password" );
        User user = dbHandler.findUserByUserName( emf, userEmail );
        if (user != null) {
            if (pswd.equals( user.getPassWord() )) {
                request.session(true);
                request.session().attribute( "id", user.getId() );
                request.session().attribute( "email", user.getEmail() );
                response.redirect( "/dashboard" );
            }
            return "rosszpw";
        }
        return "";
    }


    public static Object analyzeForm(Request req, Response res, EntityManagerFactory emf, UserDbHandler dbHandler) {
        //TODO validate input

        //TODO analise the result and set personality
        //personality is found here, but need to set it for the user
        System.out.println((String) req.session().attribute("email"));
        User user = dbHandler.findUserByUserName(emf, req.session().attribute("email"));
        System.out.println(user.toString());
        int personalityType = findPersonality( req );
        dbHandler.updateUserPersonality(user, emf, personalityType );
        System.out.println( "personality type of the user: " + personalityType );

        //TODO popup thx for filling the form

        //redirect to front page
        res.redirect( "/user/page" );
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

    public static ModelAndView renderUserPage(Request req, Response res, UserDbHandler dbHandler, EntityManagerFactory emf) {
        Map params = new HashMap<>();
        User user = dbHandler.findUserByUserName(emf, req.session().attribute("email"));
        Personality pers = user.getPersonalityType();
        Personality optPers = user.getOptPartnerPersType();
        User optUser = dbHandler.findUserByPersonality(emf, optPers);
        String optName = optUser.getFirstName();
        params.put("currenUser", user);
        params.put("match", optUser);
        return new ModelAndView( params, "/demo" );
    }

}
