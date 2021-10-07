package com.github.makarovd89.jms.ibmmq.client;

import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;

public class JmsIbmMqClient implements JmsClient {

    private final JMSContext context;
    private final Destination destination;
    private final MessageProcessor messageProcessor;
    private final int timeout;

    public JmsIbmMqClient(
            JMSContext context,
            Destination destination,
            MessageProcessor messageProcessor,
            int timeout) {

        this.context = context;
        this.destination = destination;
        this.messageProcessor = messageProcessor;
        this.timeout = timeout;
    }

    @Override
    public void get(Path filePath) throws IOException, JMSException {
        try (JMSConsumer consumer = context.createConsumer(destination)) {
            var message = consumer.receive(timeout);
            byte[] bytes = messageProcessor.readMessage(message);
            System.out.println("Received message:\n" + message);
            Files.write(filePath, bytes, CREATE);
        }
        context.close();
    }

    @Override
    public void put(Path filePath) throws IOException, JMSException {
        var message = messageProcessor.createMessage(Files.readAllBytes(filePath));
        var producer = context.createProducer();
        producer.send(destination, message);
        System.out.println("Sent message:\n" + message);
        context.close();
    }
}
