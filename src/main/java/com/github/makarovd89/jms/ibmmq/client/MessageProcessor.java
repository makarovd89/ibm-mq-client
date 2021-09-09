package com.github.makarovd89.jms.ibmmq.client;

import javax.jms.JMSException;
import javax.jms.Message;
import java.io.UnsupportedEncodingException;

public interface MessageProcessor {

    byte[] readMessage(Message message) throws JMSException, UnsupportedEncodingException;
    Message createMessage(byte[] message) throws UnsupportedEncodingException, JMSException;
}
