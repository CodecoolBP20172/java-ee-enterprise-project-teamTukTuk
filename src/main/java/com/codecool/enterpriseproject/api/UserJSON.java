package com.codecool.enterpriseproject.api;

import java.util.ArrayList;
import java.util.List;

public class UserJSON {

    private int age;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String password;
    private String passwordAgain;
    private String preference;

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public String getPreference() {
        return preference;
    }

    public List<String> getValues() {
        List<String> values = new ArrayList<>();
        values.add(String.valueOf(this.age));
        values.add(this.email);
        values.add(this.firstName);
        values.add(this.lastName);
        values.add(this.password);
        values.add(this.passwordAgain);
        values.add(this.gender);
        values.add(this.preference);
        return values;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean containsEmptyFields() {

        for (String field: getValues()
             ) {
            System.out.println(field);
            if (field == null || field.equals("")) {
                return true;
            }
        }

        return false;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }


}
