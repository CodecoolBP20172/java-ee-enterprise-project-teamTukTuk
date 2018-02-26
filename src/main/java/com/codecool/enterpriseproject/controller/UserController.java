package com.codecool.enterpriseproject.controller;

import com.codecool.enterpriseproject.service.ChatBoxService;
import com.codecool.enterpriseproject.service.UserService;
import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import static org.mindrot.jbcrypt.BCrypt.*;
import java.nio.charset.Charset;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value ="/index", method = RequestMethod.GET)
    public String renderRegisterPage() {
        return "index";
    }

    @RequestMapping(value ="/personality", method = RequestMethod.GET)
    public String renderPersonalityTest() {
        return "personality";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String checkIfInSession(HttpSession session) {
        if (session.getAttribute("id") == null) {
            return "redirect:/index";
        }else{
            return "dashboard";
        }
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
        if(loginWithValidate(session, email, password).equals("success")) {
            return "redirect:/dashboard";
        }
        return "redirect:/index";
    }

    //    public static void checkIfInSession(Request request, Response response) {
    //        if (request.session().attribute( "id" ) == null) {
    //            response.redirect("/");
    //            halt("Unauthorized access");
    //        } else {
    //            response.redirect( "/dashboard");
    //        }
    //    }


    public static HashMap<String, String> handleRegisterInput(Request request, Response response, UserService dbHandler, EntityManagerFactory emf) {
        List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
        Map<String, String> params = toMap(pairs);

        logger.info("validation started...");
        List<String> result = validateRegister(params, dbHandler, emf);

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
            dbHandler.addObject(user, emf );
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

    private static List<String> validateRegister(Map<String, String> params, UserService dbHandler, EntityManagerFactory emf) {

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

        User potentialUser = dbHandler.findUserByEmail(emf, email);
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

    private String loginWithValidate(HttpSession session, String email, String password) {
        User user = userService.findUserByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassWord() )) {
            session.setAttribute("id", user.getId());
            session.setAttribute("email", user.getEmail());
            logger.info("session attributes set");
            return "success";
        }
        return "";
    }


    public static String analyzeForm(Request req, Response res, EntityManagerFactory emf, UserService dbHandler) {
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


    public static ModelAndView renderUserPage(Request req, Response res, ChatBoxService chatBoxService, UserService dbHandler, EntityManagerFactory emf) {
        Map params = new HashMap<>();
        User user = dbHandler.findUserByEmail(emf, req.session().attribute("email"));
        User optUser = dbHandler.findMatch(emf, user);

        if (optUser!=null) {
            ChatBox chatBox = new ChatBox(user, optUser);
            chatBoxService.addObject(chatBox, emf);
            dbHandler.setInConversation(user, true, emf);
            dbHandler.setInConversation(optUser, true, emf);
            params.put("match", optUser);
        }
        params.put("user", user);

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
