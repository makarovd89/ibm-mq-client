package com.github.makarovd89.jms.ibmmq;

import com.beust.jcommander.JCommander;
import com.github.makarovd89.jms.ibmmq.args.JmsConnectionParams;
import com.github.makarovd89.jms.ibmmq.args.OperationParams;

import javax.jms.JMSException;
import java.io.IOException;
import java.nio.file.Path;

public class ApplicationController {

    public static void main(String[] args) {
        try {
            var jmsClientProperties = new JmsConnectionParams();
            var operationParams  = new OperationParams();
            var jCommander = JCommander.newBuilder()
                    .addObject(jmsClientProperties)
                    .addObject(operationParams)
                    .build();
            jCommander.parse(args);
            if (operationParams.isHelp()) {
                jCommander.usage();
                return;
            }
            run(jmsClientProperties, operationParams);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static void run(JmsConnectionParams jmsClientProperties, OperationParams operationParams) throws JMSException, IOException {
        var jmsClient = createJmsService(jmsClientProperties);
        var filePath = Path.of(operationParams.getFilePath());
        var fileEncoding = operationParams.getFileEncoding();
        switch (operationParams.getOperation()) {
            case GET: jmsClient.get(filePath, fileEncoding);
                break;
            case PUT: jmsClient.put(filePath, fileEncoding);
                break;
        }
    }

    private static JmsClient createJmsService(JmsConnectionParams jmsConnectionParams) throws JMSException {
        return new JmsClient(
                jmsConnectionParams.getHost(),
                jmsConnectionParams.getPort(),
                jmsConnectionParams.getChannel(),
                jmsConnectionParams.getQueueManager(),
                jmsConnectionParams.getUser(),
                jmsConnectionParams.getPassword(),
                jmsConnectionParams.getQueue()
        );
    }
}
