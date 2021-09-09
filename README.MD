### It is a simple IBM MQ client for testing and development

### Build jar with [Maven](https://maven.apache.org/)

`mvn clean package`

### Examples for testing

#### Run [IBM MQ container](https://hub.docker.com/r/ibmcom/mq)

`docker run --env LICENSE=accept --env MQ_QMGR_NAME=QM1 --env MQ_ENABLE_METRICS=true --publish 21414:1414 ibmcom/mq:9.1.3.0`

#### Show description of command line arguments

`java -jar ibm-mq-client.jar --help`

#### Send message

`java -jar ibm-mq-client.jar --operation put --file-path msg.txt --host localhost --port 21414 --channel DEV.APP.SVRCONN --queue-manager QM1 --queue DEV.QUEUE.1`

#### Receive message

`java -jar ibm-mq-client.jar --operation get --file-path $(date -u +%Y-%m-%dT%H:%M:%SZ).txt --host localhost --port 21414 --channel DEV.APP.SVRCONN --queue-manager QM1 --queue DEV.QUEUE.1`


