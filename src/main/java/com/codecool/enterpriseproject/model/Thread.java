package com.codecool.enterpriseproject.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class Thread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int thread_id;

    @OneToMany
    private int first_user;

    @OneToMany
    private int second_user;

    public Thread(int first_user, int second_user) {
        this.first_user = first_user;
        this.second_user = second_user;
    }

    public int getThread_id() {
        return thread_id;
    }

    public int getFirst_user() {
        return first_user;
    }

    public void setFirst_user(int first_user) {
        this.first_user = first_user;
    }

    public int getSecond_user() {
        return second_user;
    }

    public void setSecond_user(int second_user) {
        this.second_user = second_user;
    }
}
