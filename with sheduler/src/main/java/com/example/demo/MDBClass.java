package com.example.demo;

import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

@MessageDriven(name = "MDBClass",
        activationConfig = {
                @ActivationConfigProperty(
                        propertyName = "destination",
                        propertyValue = "java:jboss/exported/jms/queue/testingQ"
                ),
                @ActivationConfigProperty(
                        propertyName = "destinationType",
                        propertyValue = "javax.jms.Queue"
                )
        })
public class MDBClass implements MessageListener{
    private final static Logger LOGGER = Logger.getLogger(MDBClass.class.toString());

    @EJB
    private SessionClass sessionClass;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = null;
        try{
            if(message instanceof TextMessage){
                textMessage = (TextMessage) message;
                sessionClass.processMessage(textMessage.getText());
                LOGGER.info("Received Message from queue: " + textMessage.getText());
            }
            else {
                LOGGER.warning("Message of wrong type: " + message.getClass().getName());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
