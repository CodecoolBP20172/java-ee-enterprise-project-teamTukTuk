package com.codecool.enterpriseproject.repository;

import com.codecool.enterpriseproject.model.Gender;
import com.codecool.enterpriseproject.model.Personality;
import com.codecool.enterpriseproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUsersByAgeBetweenAndPersonalityTypeAndGenderAndPartnerGenderAndInConversationFalse(int min, int max, Personality personality, Gender gender, Gender partnerGender);

    User findUserByEmail(String email);

    User findUserByPersonalityType(Personality personality);

}
