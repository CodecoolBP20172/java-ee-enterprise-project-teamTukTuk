package com.codecool.enterpriseproject.controller;

import com.codecool.enterpriseproject.dbhandler.ChatBoxDbHandler;
import com.codecool.enterpriseproject.dbhandler.UserDbHandler;
import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.Message;
import com.codecool.enterpriseproject.model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatController {

    public static String renderTestChat(UserDbHandler dbHandler, EntityManager em) {
        //this uses the old constructor in the user class made for test users.

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

    public static ModelAndView renderChatPage(Request request, Response response, UserDbHandler dbHandler, ChatBoxDbHandler chatBoxDbHandler, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        Map params = new HashMap<>();
        User user = dbHandler.findUserByUserName(em, request.session().attribute("email"));
        ChatBox chatBox = chatBoxDbHandler.getChatBox( user, emf );
        int threadId = chatBox.getId();
        List<Message> messages = chatBoxDbHandler.getMessages( threadId, emf );
        // TODO find the optimal match, for create the chatbox
        //ChatBox chatBox = new ChatBox( user, match );

        return new ModelAndView( params, "/dashboard" );
    }
}
