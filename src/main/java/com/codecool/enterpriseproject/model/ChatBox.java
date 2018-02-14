package com.codecool.enterpriseproject.model;

import javax.persistence.*;

@NamedQueries({
        //query = "SELECT u FROM User AS u WHERE u.email = :email"
        @NamedQuery(name = "chatBox.getUsersWeMet", query = "SELECT c FROM ChatBox AS c WHERE c.firstUser = :user")})
@Entity
public class ChatBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int threadId;

    @OneToOne
    private User firstUser;

    @OneToOne
    private User secondUser;

    private boolean active;

    public ChatBox(User firstUser, User secondUser) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        active = true;
    }

    public ChatBox() {
    }

    public int getId() {
        return threadId;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(User firstUser) {
        this.firstUser = firstUser;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }
}
