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

import static spark.Spark.redirect;

public class ChatController {

    
    public static ModelAndView renderChatPage(Request request, Response response, UserDbHandler dbHandler, ChatBoxDbHandler chatBoxDbHandler, EntityManagerFactory emf) {
        Map params = new HashMap<>();
        User user = dbHandler.findUserByEmail(emf, request.session().attribute("email"));

        ChatBox chatBox = chatBoxDbHandler.getChatBox( user, emf );
        int threadId = chatBox.getId();
        List<Message> messages = chatBoxDbHandler.getMessages( threadId, emf );
        params.put("messages", messages);
        params.put("user", user);
        System.out.println(messages);
        return new ModelAndView( params, "/dashboard" );
    }

    public static String writeMessageIntoDB(Request req, Response res, UserDbHandler UdbHandler, ChatBoxDbHandler dbHandler,EntityManagerFactory emf ) {
        String text = req.queryParams("message");
        User user = UdbHandler.findUserByEmail(emf, req.session().attribute("email"));
        ChatBox chatBox = dbHandler.getChatBox(user, emf);
        Message message = new Message(chatBox, new Date(), text, user);
        dbHandler.addNewMessage(message, emf);
        res.redirect("/dashboard");
        return "";
    }
}
