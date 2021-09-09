package com.github.makarovd89.jms.ibmmq.client;

import com.github.makarovd89.jms.ibmmq.args.JmsParams;
import com.github.makarovd89.jms.ibmmq.args.OperationParams;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;

import static com.ibm.msg.client.jms.JmsConstants.PASSWORD;
import static com.ibm.msg.client.jms.JmsConstants.USERID;
import static com.ibm.msg.client.jms.JmsConstants.USER_AUTHENTICATION_MQCSP;
import static com.ibm.msg.client.jms.JmsConstants.WMQ_PROVIDER;
import static com.ibm.msg.client.wmq.common.CommonConstants.*;

public class JmsClientFactory {

    public JmsIbmMqClient createJmsClient(JmsParams jmsParams, OperationParams operationParams) throws JMSException {
        var context = getJmsContext(jmsParams);
        var destination = context.createQueue("queue:///" + jmsParams.getQueue());
        var messageProcessor = messageProcessor(context, jmsParams, operationParams);
        return new JmsIbmMqClient(context, destination, messageProcessor);
    }

    private MessageProcessor messageProcessor(JMSContext context, JmsParams jmsParams, OperationParams operationParams) {
        switch (jmsParams.getMessageType()) {
            case BYTES: return new BytesMessageProcessor(context);
            case TEXT:
            default: return new TextMessageProcessor(context, operationParams.getEncoding());
        }
    }

    private JMSContext getJmsContext(JmsParams jmsParams) throws JMSException {
        var jmsFactoryFactory = JmsFactoryFactory.getInstance(WMQ_PROVIDER);
        var connectionFactory = jmsFactoryFactory.createConnectionFactory();
        connectionFactory.setStringProperty(WMQ_HOST_NAME, jmsParams.getHost());
        connectionFactory.setIntProperty(WMQ_PORT, jmsParams.getPort());
        connectionFactory.setStringProperty(WMQ_CHANNEL, jmsParams.getChannel());
        connectionFactory.setIntProperty(WMQ_CONNECTION_MODE, WMQ_CM_CLIENT);
        connectionFactory.setStringProperty(WMQ_QUEUE_MANAGER, jmsParams.getQueueManager());
        connectionFactory.setStringProperty(WMQ_APPLICATIONNAME, "JmsClient");
        connectionFactory.setBooleanProperty(USER_AUTHENTICATION_MQCSP, true);
        connectionFactory.setStringProperty(USERID, jmsParams.getUser());
        connectionFactory.setStringProperty(PASSWORD, jmsParams.getPassword());
        return connectionFactory.createContext();
    }
}
