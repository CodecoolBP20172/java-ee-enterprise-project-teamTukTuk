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

    
    public static ModelAndView renderChatPage(Request request, Response response, UserDbHandler dbHandler, ChatBoxDbHandler chatBoxDbHandler, EntityManagerFactory emf) {
        Map params = new HashMap<>();
        User user = dbHandler.findUserByUserName(emf, request.session().attribute("email"));
        ChatBox chatBox = chatBoxDbHandler.getChatBox( user, emf );
        int threadId = chatBox.getId();
        List<Message> messages = chatBoxDbHandler.getMessages( threadId, emf );
        // TODO find the optimal match, for create the chatbox
        //ChatBox chatBox = new ChatBox( user, match );

        return new ModelAndView( params, "/dashboard" );
    }
}
