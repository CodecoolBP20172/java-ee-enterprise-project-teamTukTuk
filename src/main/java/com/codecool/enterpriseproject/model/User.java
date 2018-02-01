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
    private String gender;
    private String partnerGender = "apache helicopter";

    @Enumerated(EnumType.STRING)
    private Personality personalityType;

    @Enumerated(EnumType.STRING)
    private Personality optPartnerPT;

    private String passWord;
    private boolean inConversation = false;

    public User(String firstName, String lastName, int age, String passWord) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.passWord = passWord;
    }

    public User(String firstName, String lastName, int age, String passWord, int personality, String gender, String partnerGender) {
        //to create test users
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.passWord = passWord;
        this.gender = gender;
        this.partnerGender = partnerGender;
        setOptPartnerPT(personality);
    }

    public User() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setOptPartnerPT(int personality) {
        switch (personality) {
            case 1 :
                this.personalityType = Personality.REFORMER;
                this.optPartnerPT = partnerGender.equals("Male") ? Personality.ACHIEVER : Personality.INVESTIGATOR;
                break;
            case 2 :
                this.personalityType = Personality.HELPER;
                this.optPartnerPT = partnerGender.equals("Male") ? Personality.LOYALIST : Personality.ACHIEVER;
                break;
            case 3 :
                this.personalityType = Personality.ACHIEVER;
                this.optPartnerPT = partnerGender.equals("Male") ? Personality.HELPER : Personality.REFORMER;
                break;
            case 4 :
                this.personalityType = Personality.INDIVIDUALIST;
                this.optPartnerPT = Personality.INDIVIDUALIST;
                break;
            case 5 :
                this.personalityType = Personality.INVESTIGATOR;
                this.optPartnerPT = partnerGender.equals("Male") ? Personality.REFORMER : Personality.ENTHUSIAST;
                break;
            case 6 :
                this.personalityType = Personality.LOYALIST;
                this.optPartnerPT = partnerGender.equals("Male") ? Personality.CHALLENGER : Personality.HELPER;
                break;
            case 7 :
                this.personalityType = Personality.ENTHUSIAST;
                this.optPartnerPT = partnerGender.equals("Male") ? Personality.INVESTIGATOR : Personality.PEACEMAKER;
                break;
            case 8 :
                this.personalityType = Personality.CHALLENGER;
                this.optPartnerPT = partnerGender.equals("Male") ? Personality.PEACEMAKER : Personality.LOYALIST;
                break;
            case 9 :
                this.personalityType = Personality.PEACEMAKER;
                this.optPartnerPT = partnerGender.equals("Male") ? Personality.ENTHUSIAST : Personality.CHALLENGER;
                break;
        }
    }
}
