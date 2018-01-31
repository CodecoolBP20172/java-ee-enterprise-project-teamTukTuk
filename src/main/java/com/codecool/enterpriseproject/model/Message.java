package com.codecool.enterpriseproject.model;

import javax.persistence.*;
import java.util.Date;

public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private int thread_id;

    private Date birth_date;

    private String message;

    @ManyToOne
    private int user_id;


    public Message(int thread_id, Date birth_date, String message, int user_id) {
        this.thread_id = thread_id;
        this.birth_date = birth_date;
        this.message = message;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThread_id() {
        return thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", thread_id=" + thread_id +
                ", birth_date=" + birth_date +
                ", message='" + message + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}