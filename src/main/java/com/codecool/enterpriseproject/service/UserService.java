package com.codecool.enterpriseproject.service;

import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.Gender;
import com.codecool.enterpriseproject.model.Personality;
import com.codecool.enterpriseproject.model.User;
import com.codecool.enterpriseproject.repository.UserRepository;
import com.codecool.enterpriseproject.util.MatchFinderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MatchFinderUtil matchFinderUtil;

    @Autowired
    ChatBoxService chatBoxService;

    UserService() {

    }

    //TODO make this method dynamic
    public void updateUserPersonality(User user, int personality) {
        user.setPersonalityType(personality);
        user.setOptPartnerPersType(personality);
        userRepository.save(user);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User findUserByPersonality(Personality pers) {
        return userRepository.findUserByPersonalityType(pers);
    }

    public User findMatch(User user) {
        //find a match with max 5 years difference
        int maxDifference = 5;
        int minPartnerAge = user.getAge() - maxDifference;
        int maxPartnerAge = user.getAge() + maxDifference;
        Gender gender = user.getGender();
        Gender partnerGender = user.getPartnerGender();
        Personality optPartnerPersType = user.getOptPartnerPersType();

        List<User> matches = userRepository.findUsersByAgeBetweenAndPersonalityTypeAndGenderAndPartnerGenderAndInConversationFalse(minPartnerAge, maxPartnerAge, optPartnerPersType, partnerGender, gender);

        List<ChatBox> chatBoxes = chatBoxService.findPastChatBoxes(user);
        return matchFinderUtil.findTheOne(matches, chatBoxes);
    }

    public User findUserById(long id) {
        return userRepository.findOne(id);
    }


    public void setInConversation(User user, boolean bool) {
        user.setInConversation(bool);
        userRepository.save(user);
    }
}
