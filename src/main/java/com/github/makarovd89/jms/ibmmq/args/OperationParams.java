package com.github.makarovd89.jms.ibmmq.args;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import static com.github.makarovd89.jms.ibmmq.args.OperationParams.Operation.GET;
import static com.github.makarovd89.jms.ibmmq.args.OperationParams.Operation.PUT;

public class OperationParams {

    @Parameter(names = "--operation", description = "MQ Client operation", required = true, converter = OperationConverter.class)
    private Operation operation;

    @Parameter(names = "--file-path", description = "Path to message file", required = true)
    private String filePath;

    @Parameter(names = "--file-encoding", description = "Encoding of file")
    private String fileEncoding = "UTF-8";

    @Parameter(names = "--help", help = true)
    private boolean help = false;

    public Operation getOperation() {
        return operation;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileEncoding() {
        return fileEncoding;
    }

    public boolean isHelp() {
        return help;
    }

    public enum Operation {
        PUT, GET
    }

    public static class OperationConverter implements IStringConverter<Operation> {
        @Override
        public Operation convert(String value) {
            var operationConvertedValue = Operation.valueOf(value.toUpperCase());
            if (operationConvertedValue == null) {
                throw new ParameterException(String.format(
                        "Value: %s Operation can not be converted to Operation. Available values are %s or %s",
                        value, PUT, GET)
                );
            }
            return operationConvertedValue;
        }
    }
}
