package com.codecool.enterpriseproject.model;

import javax.persistence.*;

@Entity
public class ChatBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int thread_id;

    @OneToOne
    private User first_user;

    @OneToOne
    private User second_user;

    public ChatBox(User first_user, User second_user) {
        this.first_user = first_user;
        this.second_user = second_user;
    }

    public ChatBox() {
    }

    public int getId() {
        return thread_id;
    }

    public User getFirst_user() {
        return first_user;
    }

    public void setFirst_user(User first_user) {
        this.first_user = first_user;
    }

    public User getSecond_user() {
        return second_user;
    }

    public void setSecond_user(User second_user) {
        this.second_user = second_user;
    }
}
