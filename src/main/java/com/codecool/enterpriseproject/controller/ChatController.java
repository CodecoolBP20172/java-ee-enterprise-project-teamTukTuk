package com.codecool.enterpriseproject.controller;

import com.codecool.enterpriseproject.dbhandler.UserDbHandler;
import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.Message;
import com.codecool.enterpriseproject.model.User;

import javax.persistence.EntityManager;
import java.util.Date;

public class ChatController {

    public static String renderTestChat(UserDbHandler dbHandler, EntityManager em) {
        User sanyika = new User( "sanyika", "abarótistartvből", "sanyika@email.com", 17, "pass", 1, "Male", "Female" );
        User jolika = new User( "jolika", "sanyiszerelme", "jolika@email.com", 16, "pass", 3, "Female", "Male" );
        dbHandler.add( sanyika, em );
        dbHandler.add( jolika, em );
        ChatBox chatBox = new ChatBox( sanyika, jolika );
        Message sanyiÜzenete = new Message( chatBox, new Date(), "ez a message", sanyika );
        dbHandler.add( chatBox, em );
        dbHandler.add( sanyiÜzenete, em );
        return "siker";
    }
}
