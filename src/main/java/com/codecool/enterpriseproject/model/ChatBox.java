package com.codecool.enterpriseproject.model;

import javax.persistence.*;
@NamedQueries({
        @NamedQuery( name = "chatbox.getChatBox", query = "SELECT c FROM ChatBox c WHERE (c.firstUser = :user OR c.secondUser = :user) AND c.active = true "),
        @NamedQuery(name = "chatBox.getUsersWeMet", query = "SELECT c FROM ChatBox AS c WHERE c.firstUser = :user"),
        @NamedQuery(name = "ChatBox.getChatBoxById", query = "SELECT c FROM ChatBox AS c WHERE c.threadId = :id")} )


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
        this.active = true;
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

    public void deactivateChatBox() {
        this.active = false;
    }
}
