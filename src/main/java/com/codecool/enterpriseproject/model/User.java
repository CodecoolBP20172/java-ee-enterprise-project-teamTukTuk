package com.codecool.enterpriseproject.model;

import javax.persistence.*;
import java.util.Date;

@NamedQueries( {@NamedQuery( name = "user.getUserByEmail", query = "SELECT u FROM User AS u WHERE u.email = :email" )} )
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
    private String email;
    private boolean inConversation;

    public User(String firstName, String lastName, int age, String passWord, String email, boolean inConversation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.passWord = passWord;
        this.inConversation = inConversation;
    }

    public User() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassWord() {
        return passWord;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", passWord='" + passWord + '\'' +
                ", email='" + email + '\'' +
                ", inConversation=" + inConversation +
                '}';
    }
}
