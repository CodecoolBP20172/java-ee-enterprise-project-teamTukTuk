package com.codecool.enterpriseproject.api;

import com.codecool.enterpriseproject.model.User;
import com.codecool.enterpriseproject.service.UserService;
import com.codecool.enterpriseproject.session.UserSession;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mindrot.jbcrypt.BCrypt.gensalt;
import static org.mindrot.jbcrypt.BCrypt.hashpw;

@RestController
public class AuthController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    UserSession session;

    @Autowired
    UserService userService;

    private static HashMap<String, String> createHashMap(List<String> list, boolean success) {
        HashMap<String, String> result = new HashMap<>();

        if (success) {
            result.put("success", "true");
        }
        Integer counter = 0;
        for (String listItem : list
                ) {
            result.put(counter.toString(), listItem);
            counter++;
        }
        return result;
    }

    private static boolean checkForEmptyFields(UserJSON json) {
        for (String field : json.getValues()) {
            if (field.equals("")) {
                return false;
            }
        }
        return true;
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public String login(@RequestParam("email") String email, @RequestParam("password") String password) {
        if (loginWithValidate(email, password).equals("success")) {
            return "redirect:dashboard";
        }
        return "redirect:index";
    }

    @RequestMapping(value = "/api/register", method = RequestMethod.POST)
    public HashMap<String, String> handleRegisterInput(@RequestBody UserJSON json) {

        logger.info("validation started...");
        List<String> result = validateRegister(json);

        if (result.isEmpty()) {

            String hashedPassword = hashpw(json.getPassword(), gensalt());
            User user = new User(
                    json.getFirstName().trim(),
                    json.getLastName().trim(),
                    json.getAge(),
                    hashedPassword,
                    json.getEmail(),
                    false,
                    json.getGender(),
                    json.getPreference()
            );
            userService.addUser(user);
            logger.info("form data is valid.");
            result.add("Your account has been created!");
            return createHashMap(result, true);
        } else {

            for (String error : result
                    ) {
                logger.error(error);
            }
            return createHashMap(result, false);
        }

    }

    protected List<String> validateRegister(UserJSON json) {

        List<String> issues = new ArrayList<>();

        if (!checkForEmptyFields(json)) {
            issues.add("All fields are required!");
            return issues;
        }

        String email = json.getEmail().trim();
        String firstName = json.getFirstName().trim();
        String lastName = json.getLastName().trim();
        String password = json.getPassword();
        String passwordAgain = json.getPasswordAgain();


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

        User potentialUser = userService.findUserByEmail(email);
        logger.info("> checking if email exists");
        if (potentialUser != null) {
            issues.add("The given email already exists!");
        }

        int age;
        try {
            age = json.getAge();
        } catch (NumberFormatException ex) {
            issues.add("Age could not be parsed!");
            return issues;
        }

        if (age < 1 || age > 100) {
            issues.add("Age is outside the reasonable interval!");
        }

        logger.info("validation finished.");
        return issues;
    }

    public String loginWithValidate( String email, String password) {
        User user = userService.findUserByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassWord())) {
            session.setAttribute("id", String.valueOf(user.getId()));
            session.setAttribute("email", user.getEmail());
            logger.info("session attributes set");
            return "success";
        }
        return "";
    }

}
