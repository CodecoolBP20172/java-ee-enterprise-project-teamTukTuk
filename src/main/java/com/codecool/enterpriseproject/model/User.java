package com.codecool.enterpriseproject.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "user.getUserByEmail", query = "SELECT u FROM User AS u WHERE u.email = :email"),
        @NamedQuery(name = "user.getUserById", query = "SELECT u FROM User AS u WHERE u.id = :id"),
        @NamedQuery(name = "user.getUserByPersonality", query = "SELECT u FROM User AS u WHERE u.personalityType = :pers"),
        @NamedQuery(name = "user.findMatch", query = "SELECT u FROM User AS u WHERE u.age >= :minPartnerAge and u.age <= :maxPartnerAge and u.gender = :partnerGender and u.partnerGender = :gender and u.personalityType = :optPartnerPersType and u.inConversation = false")})
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "partner_gender")
    private Gender partnerGender = Gender.male;

    @Enumerated(EnumType.STRING)
    @Column(name = "personality_type")
    private Personality personalityType;

    @Enumerated(EnumType.STRING)
    @Column(name = "opt_partner_pers_type")
    private Personality optPartnerPersType;
    private String passWord;
    private String email;

    private boolean inConversation;

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getPartnerGender() {
        return partnerGender;
    }

    public void setPartnerGender(Gender partnerGender) {
        this.partnerGender = partnerGender;
    }

    public int getAge() {

        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User(String firstName, String lastName, int age, String passWord, String email, boolean inConversation, String gender, String partnerGender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.passWord = passWord;
        this.inConversation = inConversation;
        this.gender = setGender(gender);
        this.partnerGender = setGender(partnerGender);
    }

    public User(String firstName, String lastName, String email, int age, String passWord, int personality, String gender, String partnerGender, boolean isInConversation) {
        //to create test users in ChatController

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.passWord = passWord;
        this.gender = setGender(gender);
        this.partnerGender = setGender(partnerGender);
        setInConversation(isInConversation);
        setPersonalityType(personality);
        setOptPartnerPersType(personality);
    }

    public User() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public long getId() {
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Personality getOptPartnerPersType() {
        return optPartnerPersType;
    }

    public Personality getPersonalityType() {
        return personalityType;
    }

    private Gender setGender(String gender) {
        switch (gender) {
            case ("male") : return Gender.male;
            case ("female") : return Gender.female;
        }
        throw new IllegalArgumentException();
    }

    public void setPersonalityType(int personalityType) {
        switch (personalityType) {
            case 1: this.personalityType = Personality.REFORMER; break;
            case 2: this.personalityType = Personality.HELPER; break;
            case 3: this.personalityType = Personality.ACHIEVER; break;
            case 4: this.personalityType = Personality.INDIVIDUALIST; break;
            case 5: this.personalityType = Personality.INVESTIGATOR; break;
            case 6: this.personalityType = Personality.LOYALIST; break;
            case 7: this.personalityType = Personality.ENTHUSIAST; break;
            case 8: this.personalityType = Personality.CHALLENGER; break;
            case 9: this.personalityType = Personality.PEACEMAKER; break;
        }
    }

    public void setOptPartnerPersType(int optPartnerPersType) {
        //throws 500 if optimal personality type is not in db
        switch (optPartnerPersType) {
            case 1: this.optPartnerPersType = partnerGender == Gender.male ? Personality.ACHIEVER : Personality.INVESTIGATOR; break;
            case 2: this.optPartnerPersType = partnerGender == Gender.male ? Personality.LOYALIST : Personality.ACHIEVER; break;
            case 3: this.optPartnerPersType = partnerGender == Gender.male ? Personality.HELPER : Personality.REFORMER; break;
            case 4: this.optPartnerPersType = Personality.INDIVIDUALIST; break;
            case 5: this.optPartnerPersType = partnerGender == Gender.male ? Personality.REFORMER : Personality.ENTHUSIAST; break;
            case 6: this.optPartnerPersType = partnerGender == Gender.male ? Personality.CHALLENGER : Personality.HELPER; break;
            case 7: this.optPartnerPersType = partnerGender == Gender.male ? Personality.INVESTIGATOR : Personality.PEACEMAKER; break;
            case 8: this.optPartnerPersType = partnerGender == Gender.male ? Personality.PEACEMAKER : Personality.LOYALIST; break;
            case 9: this.optPartnerPersType = partnerGender == Gender.male ? Personality.ENTHUSIAST : Personality.CHALLENGER; break;
        }
    }

    public boolean isInConversation() {
        return inConversation;
    }

    public void setInConversation(boolean bool) {
        this.inConversation = bool;
    }
}
