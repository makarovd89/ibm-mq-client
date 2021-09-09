package com.github.makarovd89.jms.ibmmq.args;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import static com.github.makarovd89.jms.ibmmq.args.JmsParams.MessageType.BYTES;
import static com.github.makarovd89.jms.ibmmq.args.JmsParams.MessageType.TEXT;

public class JmsParams {

    @Parameter(names = {"--host"}, description = "host", required = true)
    private String host;

    @Parameter(names = {"--port"}, description = "port", required = true)
    private int port;

    @Parameter(names = {"--channel"}, description = "channel", required = true)
    private String channel;

    @Parameter(names = {"--queue-manager"}, description = "queue-manager", required = true)
    private String queueManager;

    @Parameter(names = {"--user"}, description = "user")
    private String user;

    @Parameter(names = {"--password"}, description = "password", password = true)
    private String password;

    @Parameter(names = {"--queue"}, description = "queue", required = true)
    private String queue;

    @Parameter(names = {"--message-type"}, description = "message-type", converter = MessageTypeConverter.class)
    private MessageType messageType = BYTES;

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

    public MessageType getMessageType() {
        return messageType;
    }

    public enum MessageType {
        TEXT, BYTES
    }

    public static class MessageTypeConverter implements IStringConverter<MessageType> {
        @Override
        public MessageType convert(String value) {
            try {
                return MessageType.valueOf(value.toUpperCase());
            } catch (Exception e) {
                throw new ParameterException(
                        String.format(
                                "Value: %s MessageType can not be converted to MessageType. Available values are %s or %s",
                                value, TEXT, BYTES
                        )
                );
            }
        }
    }
}
