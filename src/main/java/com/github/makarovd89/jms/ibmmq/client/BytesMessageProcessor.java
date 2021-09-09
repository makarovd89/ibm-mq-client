package com.github.makarovd89.jms.ibmmq.client;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;

public class BytesMessageProcessor implements MessageProcessor {

    private final JMSContext context;

    public BytesMessageProcessor(JMSContext context) {
        this.context = context;
    }

    @Override
    public byte[] readMessage(Message message) throws JMSException {
        return message.getBody(byte[].class);
    }

    @Override
    public Message createMessage(byte[] message) throws JMSException {
        var result = context.createBytesMessage();
        result.writeBytes(message);
        return result;
    }
}
