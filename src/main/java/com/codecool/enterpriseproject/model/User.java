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

    public User(String firstName, String lastName, int age, String passWord) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.passWord = passWord;
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
