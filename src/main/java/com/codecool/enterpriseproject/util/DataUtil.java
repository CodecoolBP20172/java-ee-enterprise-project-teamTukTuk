package com.codecool.enterpriseproject.util;

import com.codecool.enterpriseproject.dbhandler.UserDbHandler;
import com.codecool.enterpriseproject.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtil {

    private static final Logger logger = LoggerFactory.getLogger(DataUtil.class);


    public static List<String> validateRegister(Map<String, String> params, UserDbHandler dbHandler, EntityManagerFactory emf) {

        List<String> issues = new ArrayList<>();

        String[] keys = {"gender", "preference"};
        if(!checkForEmptyFields(params, keys)) {
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


    private static boolean checkForEmptyFields(Map<String, String> fields, String[] keys) {

        for (String key: keys
             ) {
            if (!fields.containsKey(key)) {
                return false;
            }
        }

        for (String field: fields.values()
                ) {
            if(field.equals("")) {

                return false;
            }
        }

        return true;
    }


    public static HashMap<String, String> createHashMap(List<String> list, boolean success) {
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
}
