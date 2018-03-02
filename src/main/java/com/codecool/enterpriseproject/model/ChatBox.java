package com.codecool.enterpriseproject.model;

import javax.persistence.*;
@NamedQueries({
        @NamedQuery( name = "chatbox.getChatBox", query = "SELECT c FROM ChatBox c WHERE (c.firstUser = :user OR c.secondUser = :user) AND c.active = true "),
        @NamedQuery(name = "chatBox.getUsersWeMet", query = "SELECT c FROM ChatBox AS c WHERE c.firstUser = :user"),
        @NamedQuery(name = "ChatBox.getChatBoxById", query = "SELECT c FROM ChatBox AS c WHERE c.threadId = :id")} )


@Entity
@Table(name = "chatbox")
public class ChatBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long threadId;

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

    public long getId() {
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

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void deactivateChatBox() {
        this.active = false;
    }

    @Override
    public String toString() {
        return "ChatBox{" +
                "threadId=" + threadId +
                ", firstUser=" + firstUser +
                ", secondUser=" + secondUser +
                ", active=" + active +
                '}';
    }
}
