package com.example.demo;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SessionClass {

    @PersistenceContext
    private EntityManager entityManager;

    @Schedule(hour = "*", minute = "*", second = "*/20", persistent = false)
    public void processMessage(String message) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessage(message);
        entityManager.persist(messageEntity);
        System.out.println("Message saved: " + message);
    }

}
