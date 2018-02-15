package com.codecool.enterpriseproject.controller;

import com.codecool.enterpriseproject.dbhandler.ChatBoxDbHandler;
import com.codecool.enterpriseproject.dbhandler.UserDbHandler;
import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.Personality;
import com.codecool.enterpriseproject.model.User;
import com.codecool.enterpriseproject.util.DataUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import static org.mindrot.jbcrypt.BCrypt.*;
import java.nio.charset.Charset;
import javax.persistence.EntityManagerFactory;
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
            response.redirect("/");
            halt("Unauthorized access");
        } else {
            response.redirect("/dashboard");
        }
    }


    public static HashMap<String, String> handleRegisterInput(Request request, Response response, UserDbHandler dbHandler, EntityManagerFactory emf) {
        List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
        Map<String, String> params = toMap(pairs);

        logger.info("validation started...");
        List<String> result = DataUtil.validateRegister(params, dbHandler, emf);

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
            dbHandler.add(user, emf );
            logger.info("form data is valid.");
            result.add("Your account has been created!");
            return DataUtil.createHashMap(result, true);

        } else {

            for (String error: result
                 ) {
                logger.error(error);
            }

            return DataUtil.createHashMap(result, false);
        }
    }

    public static String loginWithValidate(Request request, Response response, UserDbHandler dbHandler, EntityManagerFactory emf) {
        logger.info("loggin in...");
        String userEmail = request.queryParams( "email" );
        String PlainPassword = request.queryParams( "password" );
        User user = dbHandler.findUserByEmail( emf, userEmail );
        System.out.println(user);
        if (user != null && BCrypt.checkpw(PlainPassword, user.getPassWord() )) {
            request.session(true);
            request.session().attribute("id", user.getId());
            request.session().attribute("email", user.getEmail());
            logger.info("session attributes set");

            return "success";
        }

        return "";
    }


    public static String analyzeForm(Request req, Response res, EntityManagerFactory emf, UserDbHandler dbHandler) {
        //TODO validate input

        //TODO analise the result and set personality
        //personality is found here, but need to set it for the user
        System.out.println((String) req.session().attribute("email"));
        User user = dbHandler.findUserByEmail(emf, req.session().attribute("email"));
        System.out.println(user.toString());
        int personalityType = findPersonality( req );
        dbHandler.updateUserPersonality(user, emf, personalityType );
        System.out.println( "personality type of the user: " + personalityType );

        //TODO popup thx for filling the form

        //redirect to front page
        res.redirect( "/user/page" );

        return "";
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


    public static String renderUserPage(Request req, Response res, ChatBoxDbHandler chatBoxDbHandler, UserDbHandler dbHandler, EntityManagerFactory emf) {
        System.out.println("RENDER JUZER PÉDZS");
        Map params = new HashMap<>();
        User user = dbHandler.findUserByEmail(emf, req.session().attribute("email"));
        User optUser = dbHandler.findMatch(emf, user);
        Personality pers = user.getPersonalityType();
        System.out.println("pers" + pers);
        if (pers == null) {
            System.out.println("wtf");
            res.redirect("/personality_test");
            params.put("user", user);
            return "";
        }
        if (optUser!=null) {
            ChatBox chatBox = new ChatBox(user, optUser);
            chatBoxDbHandler.addNewChatBox(emf, chatBox);
            dbHandler.setInConversation(user, true, emf);
            dbHandler.setInConversation(optUser, true, emf);
            params.put("match", optUser);
        }
        params.put("user", user);

        return "";
    }


    private static Map<String, String> toMap(List<NameValuePair> pairs){
        Map<String, String> map = new HashMap<>();
        for (NameValuePair pair : pairs) {
            map.put(pair.getName(), pair.getValue());
        }

        return map;
    }

}
