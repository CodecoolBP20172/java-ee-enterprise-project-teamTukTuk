package com.codecool.enterpriseproject.controller;

import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.Message;
import com.codecool.enterpriseproject.model.User;
import com.codecool.enterpriseproject.service.ChatBoxService;
import com.codecool.enterpriseproject.service.MessageService;
import com.codecool.enterpriseproject.service.UserService;
import com.codecool.enterpriseproject.session.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    ChatBoxService chatBoxService;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    UserSession session;

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String renderChatPage(Model model) {
        User user = userService.findUserByEmail(session.getAttribute("email"));
        ChatBox chatBox = chatBoxService.getChatBox(user);
        List<Message> messages = messageService.getMessages(chatBox);
        model.addAttribute("messages", messages);
        model.addAttribute("user", user);
        System.out.println(messages);
        return "dashboard";
    }

    @RequestMapping(value = "/post_message", method = RequestMethod.POST)
    public String writeMessageIntoDB(@RequestParam("message") String text) {
        User user = userService.findUserByEmail(session.getAttribute("email"));
        ChatBox chatBox = chatBoxService.getChatBox(user);
        Message message = new Message(chatBox, new Date(), text, user);
        messageService.addMessage(message);
        return "redirect:/dashboard";
    }

    @RequestMapping(value = "/doyoulikeme", method = RequestMethod.POST)
    public String getNewPartner(@RequestParam("userId") Long userId) {
        User user = userService.findUserById(userId);
        ChatBox chatBox = chatBoxService.getChatBox(user);
        User anotherUser = chatBox.getSecondUser();
        userService.setInConversation(user, false);
        userService.setInConversation(anotherUser, false);
        chatBoxService.deactivateChatBox(chatBox);
        return "redirect:/user/page";
    }
}
