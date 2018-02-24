package com.codecool.enterpriseproject.repository;

import com.codecool.enterpriseproject.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long>{
}
