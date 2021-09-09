package com.github.makarovd89.jms.ibmmq.client;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import java.io.UnsupportedEncodingException;

public class TextMessageProcessor implements MessageProcessor {

    private final JMSContext context;
    private final String encoding;

    public TextMessageProcessor(JMSContext context, String encoding) {
        this.context = context;
        this.encoding = encoding;
    }

    @Override
    public byte[] readMessage(Message message) throws JMSException, UnsupportedEncodingException {
        return message.getBody(String.class)
                .getBytes(encoding);
    }

    @Override
    public Message createMessage(byte[] message) throws UnsupportedEncodingException {
        return context.createTextMessage(
                new String(
                        message, encoding
                )
        );
    }
}
