package com.codecool.enterpriseproject.repository;

import com.codecool.enterpriseproject.model.ChatBox;
import com.codecool.enterpriseproject.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>{

    List<Message> findMessagesByChatBox_ActiveAndChatBox(Boolean bool, ChatBox chatBox);

}
