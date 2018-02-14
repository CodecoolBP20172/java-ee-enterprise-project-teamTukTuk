package com.codecool.enterpriseproject.controller;

import com.codecool.enterpriseproject.dbhandler.UserDbHandler;
import com.codecool.enterpriseproject.model.Personality;
import com.codecool.enterpriseproject.model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import static org.mindrot.jbcrypt.BCrypt.*;
import javax.persistence.EntityManager;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.halt;

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

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

    public static HashMap<String, String> handleRegisterInput(Request request, Response response, UserDbHandler dbHandler, EntityManager em) {
        List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
        Map<String, String> params = toMap(pairs);

        logger.info("validation started...");
        List<String> result = validateRegister(params, dbHandler, em);

        if(result.isEmpty()) {

            String hashedPassword = hashpw(params.get("password"), gensalt());
            User user = new User(
                    params.get("firstName").trim(),
                    params.get("lastName").trim(),
                    Integer.parseInt(params.get("age")),
                    hashedPassword,
                    params.get("email"),
                    false,
                    params.get("gender"),
                    params.get("preference")
            );
            dbHandler.add(user, em );
            logger.info("form data is valid.");
            result.add("Your account has been created!");
            return createHashMap(result, true);
        } else {

            for (String error: result
                 ) {
                logger.error(error);
            }
            return createHashMap(result, false);
        }

    }

    private static List<String> validateRegister(Map<String, String> params, UserDbHandler dbHandler, EntityManager em) {

        List<String> issues = new ArrayList<>();

        if(!checkForEmptyFields(params)) {
            issues.add("All fields are required!");
            return issues;
        }

        String email = params.get("email").trim();
        String firstName = params.get("firstName").trim();
        String lastName = params.get("lastName").trim();
        String password = params.get("password");
        String passwordAgain = params.get("passwordAgain");


        logger.info("> checking passwords");
        if (!password.equals(passwordAgain)) {
            issues.add("Passwords do not match!");
            return issues; // no point going further if passwords are wrong
        }
        logger.info("> checking name length");
        if (firstName.length() < 4 || lastName.length() < 4) {
            issues.add("Your name has to be at least 4 characters long!");
        }

        logger.info("> checking for invalid characters in name");
        if (!firstName.matches("[a-zA-Z0-9]+") || !lastName.matches("[a-zA-Z0-9]+")) {
            issues.add("Your name can only contain letters and numbers!");
        }

        User potentialUser = dbHandler.findUserByUserByEmail(em, email);
        logger.info("> checking if email exists");
        if (potentialUser != null) {
            issues.add("The given email already exists!");
        }

        int age;
        try {
            age = Integer.parseInt(params.get("age"));
        } catch(NumberFormatException ex) {
            issues.add("Age could not be parsed!");
            return issues;
        }

        if(age < 1 || age > 100) {
            issues.add("Age is outside the reasonable interval!");
        }

        logger.info("validation finished.");
        return issues;
    }

    private static boolean checkForEmptyFields(Map<String, String> fields) {

        if(!fields.containsKey("gender") || !fields.containsKey("preference")) {
            return false;
        }
        for (String field: fields.values()
             ) {
            if(field.equals("")) {
                return false;
            }
        }
        return true;
    }

    private static HashMap<String, String> createHashMap(List<String> list, boolean success) {
        HashMap<String, String> result = new HashMap<>();

        if(success) {
            result.put("success", "true");
        }
        Integer counter = 0;
        for (String listItem: list
             ) {
            result.put(counter.toString(), listItem);
            counter++;
        }
        return result;
    }

    public static String loginWithValidate(Request request, Response response, UserDbHandler dbHandler, EntityManager em) {
        String userEmail = request.queryParams( "email" );
        String PlainPassword = request.queryParams( "password" );
        User user = dbHandler.findUserByUserByEmail( em, userEmail );

        if (user != null && BCrypt.checkpw(PlainPassword, user.getPassWord() )) {
            request.session(true);
            request.session().attribute("id", user.getId());
            request.session().attribute("email", user.getEmail());
            return "success";
        }
        return "";
    }


    public static Object analyzeForm(Request req, Response res, EntityManager em, UserDbHandler dbHandler) {
        //TODO validate input

        //TODO analise the result and set personality
        //personality is found here, but need to set it for the user
        System.out.println((String) req.session().attribute("email"));
        User user = dbHandler.findUserByUserByEmail(em, req.session().attribute("email"));
        System.out.println(user.toString());
        int personalityType = findPersonality( req );
        dbHandler.updateUserPersonality(user, em, personalityType );
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

    public static ModelAndView renderUserPage(Request req, Response res, UserDbHandler dbHandler, EntityManager em) {
        Map params = new HashMap<>();
        User user = dbHandler.findUserByUserByEmail(em, req.session().attribute("email"));
        Personality pers = user.getPersonalityType();
        Personality optPers = user.getOptPartnerPersType();
        User optUser = dbHandler.findUserByPersonality(em, optPers);
        String optName = optUser.getFirstName();
        params.put("currenUser", user);
        params.put("match", optUser);

        return new ModelAndView( params, "/demo" );
    }

    private static Map<String, String> toMap(List<NameValuePair> pairs){
        Map<String, String> map = new HashMap<>();
        for(int i=0; i<pairs.size(); i++){
            NameValuePair pair = pairs.get(i);
            map.put(pair.getName(), pair.getValue());
        }
        return map;
    }

}
