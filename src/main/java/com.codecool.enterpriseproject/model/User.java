package com.codecool.enterpriseproject.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private String nickName;
    private Date birthDate;
    private String passWord;
    private boolean inConversation = false;

    public User(String firstName, String lastName, String nickName, Date birthDate, String passWord) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.birthDate = birthDate;
        this.passWord = passWord;
    }

}
