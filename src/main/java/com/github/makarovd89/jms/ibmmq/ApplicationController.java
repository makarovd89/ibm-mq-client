package com.github.makarovd89.jms.ibmmq;

import com.beust.jcommander.JCommander;
import com.github.makarovd89.jms.ibmmq.args.JmsParams;
import com.github.makarovd89.jms.ibmmq.args.OperationParams;
import com.github.makarovd89.jms.ibmmq.client.JmsClientFactory;

import javax.jms.JMSException;
import java.io.IOException;
import java.nio.file.Path;

public class ApplicationController {

    private JmsClientFactory jmsClientFactory;

    public ApplicationController(JmsClientFactory jmsClientFactory) {
        this.jmsClientFactory = jmsClientFactory;
    }

    public static void main(String[] args) {
        try {
            var jmsConnectionParams = new JmsParams();
            var operationParams  = new OperationParams();
            var jCommander = JCommander.newBuilder()
                    .addObject(jmsConnectionParams)
                    .addObject(operationParams)
                    .build();
            jCommander.parse(args);
            if (operationParams.isHelp()) {
                jCommander.usage();
                return;
            }

            new ApplicationController(new JmsClientFactory())
                    .run(
                            jmsConnectionParams, operationParams
                    );
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void run(JmsParams jmsClientProperties, OperationParams operationParams) throws JMSException, IOException {
        var jmsClient = jmsClientFactory.createJmsClient(
                jmsClientProperties, operationParams
        );
        var filePath = Path.of(operationParams.getFilePath());
        switch (operationParams.getOperation()) {
            case GET: jmsClient.get(filePath);
                break;
            case PUT: jmsClient.put(filePath);
                break;
        }
    }
}
