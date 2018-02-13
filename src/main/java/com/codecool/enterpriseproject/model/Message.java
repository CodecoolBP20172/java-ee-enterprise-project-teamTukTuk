package com.codecool.enterpriseproject.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private ChatBox chatBox;
    private Date date;
    private String message;

    @ManyToOne
    private User user;


    public Message(ChatBox chatBox, Date date, String message, User user) {
        this.chatBox = chatBox;
        this.date = date;
        this.message = message;
        this.user = user;
    }

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ChatBox getChatBox() {
        return chatBox;
    }

    public void setChatBox(ChatBox chatBox) {
        this.chatBox = chatBox;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", threadId=" + chatBox +
                ", date=" + date +
                ", message='" + message + '\'' +
                ", userId=" + user +
                '}';
    }
}