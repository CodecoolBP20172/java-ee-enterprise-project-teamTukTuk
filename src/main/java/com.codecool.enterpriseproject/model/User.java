package com.codecool.enterpriseproject.model;

import java.util.Date;

public class User {

    String id;
    String firstName;
    String lastName;
    String nickName;
    Date birthDate;
    String passWord;

    public User(String id, String firstName, String lastName, String nickName, Date birthDate, String passWord) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.birthDate = birthDate;
        this.passWord = passWord;
    }

}
