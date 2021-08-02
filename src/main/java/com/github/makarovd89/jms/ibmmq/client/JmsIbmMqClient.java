package com.github.makarovd89.jms.ibmmq.client;

import com.ibm.msg.client.jms.internal.JmsTextMessageImpl;

import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
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
    public void get(Path filePath, String fileEncoding) throws IOException, JMSException {
        try (JMSConsumer consumer = context.createConsumer(destination)) {
            var message = consumer.receive(15000);
            byte[] bytes;
            if (message instanceof JmsTextMessageImpl) {
                message.getBody(String.class);
                bytes = message.getBody(String.class).getBytes(fileEncoding);
            } else {
                bytes = message.getBody(byte[].class);
            }
            System.out.println("\nReceived message:\n" + message);
            Files.write(filePath, bytes, CREATE);
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
