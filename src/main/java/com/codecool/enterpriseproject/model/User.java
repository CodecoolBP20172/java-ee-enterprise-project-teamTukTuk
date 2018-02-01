package com.codecool.enterpriseproject.model;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private int age;
    private String passWord;
    private boolean inConversation = false;
    private String gender;
    private String preferredGender;
    private String personality;

    public User(String firstName, String lastName, int age, String passWord, boolean inConversation, String gender, String preferredGender, String personality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.passWord = passWord;
        this.inConversation = inConversation;
        this.gender = gender;
        this.preferredGender = preferredGender;
        this.personality = personality;
    }

    public User() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
