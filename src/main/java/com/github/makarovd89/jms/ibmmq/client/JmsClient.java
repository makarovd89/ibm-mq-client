package com.github.makarovd89.jms.ibmmq.client;

import javax.jms.JMSException;
import java.io.IOException;
import java.nio.file.Path;

public interface JmsClient {

    void get(Path filePath, String fileEncoding) throws IOException, JMSException;

    void put(Path filePath, String fileEncoding) throws IOException;
}
