package com.example.demo;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    @Resource(lookup = "java:/JmsXA")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:jboss/exported/jms/queue/testingQ")
    private Queue queue;

    public void init() {
        message = "Hello World!";

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        try(JMSContext context = (JMSContext) connectionFactory.createContext()) {
            context.createProducer().send(queue, "Hello, JMS!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
//        try(JMSContext context = (JMSContext) connectionFactory.createContext()) {
//            String message = context.createConsumer(queue).receiveBody(String.class);
//            System.out.println(message);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}