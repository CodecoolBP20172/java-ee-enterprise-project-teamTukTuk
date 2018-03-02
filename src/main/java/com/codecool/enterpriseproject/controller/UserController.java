package com.codecool.enterpriseproject.controller;

import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.User;
import com.codecool.enterpriseproject.service.ChatBoxService;
import com.codecool.enterpriseproject.service.UserService;
import com.codecool.enterpriseproject.session.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserSession session;

    @Autowired
    UserService userService;

    @Autowired
    ChatBoxService chatBoxService;


    private static Integer mostFrequent(List<String> list) {
        Map<String, Integer> counterMap = new HashMap<>();
        Integer maxValue = 0;
        Integer mostFrequentValue = null;

        for (String valueAsKey : list) {
            Integer counter = counterMap.get(valueAsKey);
            counterMap.put(valueAsKey, counter == null ? 1 : counter + 1);
            counter = counterMap.get(valueAsKey);
            if (counter > maxValue) {
                maxValue = counter;
                mostFrequentValue = Integer.parseInt(valueAsKey);
            }
        }
        return mostFrequentValue;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String renderRegisterPage() {
        return "index";
    }

    @RequestMapping(value = "/personality", method = RequestMethod.GET)
    public String renderPersonalityTest() {
        return "personality";
    }

    @RequestMapping(value = "/user/*", method = RequestMethod.GET)
    public String checkIfInSession() {
        if (session.getAttribute("id") == null) {
            return "redirect:/index";
        } else {
            return "dashboard";
        }
    }


    @RequestMapping(value = "/set_personality", method = RequestMethod.POST)
    public String analyzeForm(@RequestParam("q1") String q1,
                              @RequestParam("q2") String q2, @RequestParam("q3") String q3,
                              @RequestParam("q4") String q4, @RequestParam("q5") String q5) {
        //TODO validate input
        //TODO analise the result and set personality
        //personality is found here, but need to set it for the user
        List<String> questions = new ArrayList<>(Arrays.asList(q1, q2, q3, q4, q5));
        User user = userService.findUserByEmail(String.valueOf(session.getAttribute("email")));
        int personalityType = mostFrequent(questions);
        userService.updateUserPersonality(user, personalityType);
        logger.info("personality type of the user: " + personalityType);
        //TODO popup thx for filling the form
        //redirect to front page
        return "redirect:/user/page";
    }

    @RequestMapping(value = "/user/page", method = RequestMethod.GET)
    public String renderUserPage(Model model) {
        User user = userService.findUserByEmail(session.getAttribute("email"));
        User optUser = userService.findMatch(user);

        System.out.println("opt user: " + optUser + ", user: " + user);

        if (optUser != null) {
            ChatBox chatBox = new ChatBox(user, optUser);
            chatBoxService.addChatBox(chatBox);
            userService.setInConversation(user, true);
            userService.setInConversation(optUser, true);
            model.addAttribute("match", optUser);
        }
        model.addAttribute("user", user);
        return "redirect:/dashboard";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {
        session.clear();
        return "redirect:/";
    }
}
