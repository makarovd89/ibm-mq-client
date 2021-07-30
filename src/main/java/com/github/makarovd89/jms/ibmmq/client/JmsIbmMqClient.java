package com.github.makarovd89.jms.ibmmq.client;

import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;

public class JmsIbmMqClient implements JmsClient {

    private final JMSContext context;
    private final Destination destination;

    public JmsIbmMqClient(JMSContext context, Destination destination) {
        this.context = context;
        this.destination = destination;
    }

    @Override
    public void get(Path filePath, String fileEncoding) throws IOException {
        try (JMSConsumer consumer = context.createConsumer(destination)) {
            String receivedMessage = consumer.receiveBody(String.class, 15000);
            System.out.println("\nReceived message:\n" + receivedMessage);
            Files.write(filePath, receivedMessage.getBytes(fileEncoding), CREATE);
        }
        context.close();
    }

    @Override
    public void put(Path filePath, String fileEncoding) throws IOException {
        var message = context.createTextMessage(new String(Files.readAllBytes(filePath), fileEncoding));
        var producer = context.createProducer();
        producer.send(destination, message);
        System.out.println("Sent message:\n" + message);
        context.close();
    }
}
