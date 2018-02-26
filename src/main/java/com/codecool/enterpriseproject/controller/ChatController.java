package com.codecool.enterpriseproject.controller;

import com.codecool.enterpriseproject.service.ChatBoxService;
import com.codecool.enterpriseproject.service.MessageService;
import com.codecool.enterpriseproject.service.UserService;
import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.Message;
import com.codecool.enterpriseproject.model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatController {

    public static ModelAndView renderChatPage(Request request, Response response, UserService dbHandler, ChatBoxService chatBoxService, EntityManagerFactory emf, MessageService messageService) {
        Map params = new HashMap<>();
        User user = dbHandler.findUserByEmail(emf, request.session().attribute("email"));

        ChatBox chatBox = chatBoxService.getChatBox( user, emf );
        int threadId = chatBox.getId();
        List<Message> messages = messageService.getMessages( threadId, emf );
        params.put("messages", messages);
        params.put("user", user);
        System.out.println(messages);
        return new ModelAndView( params, "/dashboard" );
    }

    public static String writeMessageIntoDB(Request req, Response res, UserService UdbHandler, ChatBoxService dbHandler, EntityManagerFactory emf, MessageService messageService ) {
        String text = req.queryParams("message");
        User user = UdbHandler.findUserByEmail(emf, req.session().attribute("email"));
        ChatBox chatBox = dbHandler.getChatBox(user, emf);
        Message message = new Message(chatBox, new Date(), text, user);
        messageService.addObject(message, emf);
        res.redirect("/dashboard");
        return "";
    }

    public static String getNewPartner(Request request, Response response, ChatBoxService chatBoxService, EntityManagerFactory emf, UserService dbHandler) {
        System.out.println("bitch " + request.queryParams("bitchswitcher"));
        int userId = Integer.parseInt(request.queryParams("userId"));
        ChatBox chatBox = chatBoxService.getChatBox(dbHandler.findUserById(userId, emf), emf);
        User user = dbHandler.findUserById(userId, emf);
        User anotherUser = chatBox.getSecondUser();
        System.out.println("egyiok juzer: " +user.getFirstName());
        System.out.println("másik juzer: " +anotherUser.getFirstName());
        dbHandler.setInConversation(user, false, emf);
        dbHandler.setInConversation(anotherUser, false, emf);
        chatBoxService.deactivateChatBox(emf, chatBox);
        //User matchingNewPartner = dbHandler.findMatch(emf, user);
        //ChatBox newChatBox = new ChatBox(user, matchingNewPartner);
        //chatBoxService.addNewChatBox(emf, newChatBox);
        response.redirect("/user/page");
        return "";
    }
}
