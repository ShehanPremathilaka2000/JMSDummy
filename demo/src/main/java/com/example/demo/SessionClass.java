package com.example.demo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SessionClass {

    @PersistenceContext
    private EntityManager entityManager;

    public void processMessage(String message) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessage(message);
        entityManager.persist(messageEntity);
        System.out.println("Message saved: " + message);
    }

}
