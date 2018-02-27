package com.codecool.enterpriseproject.api;

import com.codecool.enterpriseproject.model.User;
import com.codecool.enterpriseproject.service.UserService;
import com.codecool.enterpriseproject.session.UserSession;
import com.codecool.enterpriseproject.util.JsonUtil;
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

    @PostMapping(value = "/api/login")
    public String login(@RequestBody LoginJSON loginData) {
        if (loginWithValidate(loginData.getEmail(), loginData.getPassword()).equals("success")) {
            loginData.setValid(true);

            //TODO SESSION HANDLING GOES HERE
        }
        return JsonUtil.toJson(loginData);
    }

    @PostMapping(value = "/api/register")
    public String handleRegisterInput(@RequestBody UserJSON registerData) {

        for (String item: registerData.getValues()
             ) {
            System.out.println(item);
        }

        logger.info("validation started...");
        ErrorJSON result = validateRegister(registerData);

        if (result.isValid()) {

            String hashedPassword = hashpw(registerData.getPassword(), gensalt());
            User user = new User(
                    registerData.getFirstName().trim(),
                    registerData.getLastName().trim(),
                    registerData.getAge(),
                    hashedPassword,
                    registerData.getEmail(),
                    false,
                    registerData.getGender(),
                    registerData.getPreference()
            );
            userService.addUser(user);
            logger.info("form data is valid.");
        }

        return JsonUtil.toJson(result);
    }

    private ErrorJSON validateRegister(UserJSON json) {

         ErrorJSON errors = new ErrorJSON();
         boolean isvalid = true;

        if (json.containsEmptyFields()) {
            errors.setAllFieldsRequired(true);
            return errors;
        }

        String email = json.getEmail().trim();
        String firstName = json.getFirstName().trim();
        String lastName = json.getLastName().trim();
        String password = json.getPassword();
        String passwordAgain = json.getPasswordAgain();


        logger.info("> checking passwords");
        if (!password.equals(passwordAgain)) {
            errors.setPasswordMismatch(true);
            return errors; // no point going further if passwords are wrong
        }
        logger.info("> checking name length");
        if (firstName.length() < 4 || lastName.length() < 4) {
            errors.setTooShortName(true);
            isvalid = false;
        }

        logger.info("> checking for invalid characters in name");
        if (!firstName.matches("[a-zA-Z0-9]+") || !lastName.matches("[a-zA-Z0-9]+")) {
            errors.setInvalidName(true);
            isvalid = false;
        }

        User potentialUser = userService.findUserByEmail(email);
        logger.info("> checking if email exists");
        if (potentialUser != null) {
            errors.setEmailExists(true);
            isvalid = false;
        }

        int age;
        try {
            age = json.getAge();
        } catch (NumberFormatException ex) {
            errors.setCouldNotParseAge(true);
            isvalid = false;
            return errors;
        }

        if (age < 1 || age > 100) {
            errors.setAgeOutsideInterval(true);
            isvalid = false;
        }

        logger.info("validation finished.");
        errors.setValid(isvalid);
        return errors;
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