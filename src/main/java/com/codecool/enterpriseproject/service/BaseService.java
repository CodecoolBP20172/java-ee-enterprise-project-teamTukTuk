package com.codecool.enterpriseproject.service;

import com.codecool.enterpriseproject.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Service
public class BaseService {

    @Autowired
    BaseRepository baseRepository;

    public void addObject(Object object) {
        baseRepository.save(object);
    }

}
