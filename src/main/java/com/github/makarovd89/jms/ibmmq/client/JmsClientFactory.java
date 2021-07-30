package com.github.makarovd89.jms.ibmmq.client;

import com.github.makarovd89.jms.ibmmq.args.JmsConnectionParams;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;

import static com.ibm.msg.client.jms.JmsConstants.PASSWORD;
import static com.ibm.msg.client.jms.JmsConstants.USERID;
import static com.ibm.msg.client.jms.JmsConstants.USER_AUTHENTICATION_MQCSP;
import static com.ibm.msg.client.jms.JmsConstants.WMQ_PROVIDER;
import static com.ibm.msg.client.wmq.common.CommonConstants.*;

public class JmsClientFactory {

    public JmsIbmMqClient createJmsClient(JmsConnectionParams jmsConnectionParams) throws JMSException {
        var context = getJmsContext(jmsConnectionParams);
        var destination = context.createQueue("queue:///" + jmsConnectionParams.getQueue());
        return new JmsIbmMqClient(context, destination);
    }

    private JMSContext getJmsContext(JmsConnectionParams jmsConnectionParams) throws JMSException {
        var jmsFactoryFactory = JmsFactoryFactory.getInstance(WMQ_PROVIDER);
        var connectionFactory = jmsFactoryFactory.createConnectionFactory();
        connectionFactory.setStringProperty(WMQ_HOST_NAME, jmsConnectionParams.getHost());
        connectionFactory.setIntProperty(WMQ_PORT, jmsConnectionParams.getPort());
        connectionFactory.setStringProperty(WMQ_CHANNEL, jmsConnectionParams.getChannel());
        connectionFactory.setIntProperty(WMQ_CONNECTION_MODE, WMQ_CM_CLIENT);
        connectionFactory.setStringProperty(WMQ_QUEUE_MANAGER, jmsConnectionParams.getQueueManager());
        connectionFactory.setStringProperty(WMQ_APPLICATIONNAME, "JmsClient");
        connectionFactory.setBooleanProperty(USER_AUTHENTICATION_MQCSP, true);
        connectionFactory.setStringProperty(USERID, jmsConnectionParams.getUser());
        connectionFactory.setStringProperty(PASSWORD, jmsConnectionParams.getPassword());
        return connectionFactory.createContext();
    }
}
