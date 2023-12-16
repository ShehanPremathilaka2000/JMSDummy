package org.example;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.Queue;
import java.util.logging.Logger;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            Context context = new InitialContext();
            log.info("Context created");
            log.info("Creating connection factory");
            ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
            log.info("Created connection factory");
            log.info("Creating queue");
            Queue queue = (Queue) context.lookup("jms/queue/testingQ");
            log.info("Created queue");
            try(JMSContext jmsContext = connectionFactory.createContext()) {
                log.info("Sending message");
                TextMessage textMessage = jmsContext.createTextMessage("Hello!");
                jmsContext.createProducer().send( queue, textMessage);
                log.info("Message sent");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}