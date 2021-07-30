package com.github.makarovd89.jms.ibmmq;

import com.ibm.msg.client.jms.JmsFactoryFactory;

import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.ibm.msg.client.jms.JmsConstants.*;
import static com.ibm.msg.client.wmq.common.CommonConstants.*;
import static java.nio.file.StandardOpenOption.CREATE;

public class JmsClient {

    private final Destination destination;
    private final JMSContext context;

    public JmsClient(String host, int port, String channel, String qmgr, String appUser, String appPassword, String queueName) throws JMSException {
        var ff = JmsFactoryFactory.getInstance(WMQ_PROVIDER);
        var cf = ff.createConnectionFactory();
        cf.setStringProperty(WMQ_HOST_NAME, host);
        cf.setIntProperty(WMQ_PORT, port);
        cf.setStringProperty(WMQ_CHANNEL, channel);
        cf.setIntProperty(WMQ_CONNECTION_MODE, WMQ_CM_CLIENT);
        cf.setStringProperty(WMQ_QUEUE_MANAGER, qmgr);
        cf.setStringProperty(WMQ_APPLICATIONNAME, "JmsClient");
        cf.setBooleanProperty(USER_AUTHENTICATION_MQCSP, true);
        cf.setStringProperty(USERID, appUser);
        cf.setStringProperty(PASSWORD, appPassword);
        context = cf.createContext();
        destination = context.createQueue("queue:///" + queueName);
    }

    public void get(Path filePath, String fileEncoding) throws IOException {
        try (JMSConsumer consumer = context.createConsumer(destination)) {
            String receivedMessage = consumer.receiveBody(String.class, 15000);
            System.out.println("\nReceived message:\n" + receivedMessage);
            Files.write(filePath, receivedMessage.getBytes(fileEncoding), CREATE);
        }
        context.close();
    }

    public void put(Path filePath, String fileEncoding) throws IOException {
        var message = context.createTextMessage(new String(Files.readAllBytes(filePath), fileEncoding));
        var producer = context.createProducer();
        producer.send(destination, message);
        System.out.println("Sent message:\n" + message);
        context.close();
    }
}
