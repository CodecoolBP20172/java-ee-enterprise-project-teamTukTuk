package com.codecool.enterpriseproject.util;

import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MatchFinderUtil {


    public User findTheOne(List<User> matches, List<ChatBox> chatBoxes) {
        User theOne = null;

        //these should be tested if you can convert Objects to ChatBoxes
        //and then to a List of Users like this
        List<User> usersMet = findUsersMet(chatBoxes);

        if (matches.isEmpty()) {
            return null;
        }
        boolean matchFound = false;
        while (!matchFound) {
            int lengthChecker = 0;
            System.out.println("matches size: " + matches.size());
            for (User match : matches) {
                lengthChecker += 1;
                if (!usersMet.contains(match)) {
                    theOne = match;
                    matchFound = true;
                }
                if (lengthChecker >= matches.size() && !matchFound) {
                    //if the user have talked to all matches we return null
                    return null;
                }
            }
        }
        return theOne;
    }

    private List<User> findUsersMet(List<ChatBox> chatboxes) {
        List<User> usersMet = new ArrayList<>();
        for (ChatBox chatBox : chatboxes) {
            usersMet.add(chatBox.getSecondUser());
        }
        return usersMet;
    }

}
