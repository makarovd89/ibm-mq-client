package com.github.makarovd89.jms.ibmmq.args;

import com.beust.jcommander.Parameter;

public class JmsConnectionParams {

    @Parameter(names = {"--host", "-h"}, description = "host", required = true)
    private String host;

    @Parameter(names = {"--port", "-p"}, description = "port", required = true)
    private int port;

    @Parameter(names = {"--channel", "-c"}, description = "channel", required = true)
    private String channel;

    @Parameter(names = {"--queue-manager", "-qm"}, description = "queue-manager", required = true)
    private String queueManager;

    @Parameter(names = {"--user", "-U"}, description = "user")
    private String user;

    @Parameter(names = {"--password", "-P"}, description = "password", password = true)
    private String password;

    @Parameter(names = {"--queue"}, description = "queue", required = true)
    private String queue;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getChannel() {
        return channel;
    }

    public String getQueueManager() {
        return queueManager;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getQueue() {
        return queue;
    }

}
