# spring-cloud-stream-sink-stomp
## For what is the Spring Cloud Stream app created for?
Data-MicroServices Application provided as Spring Cloud Stream

# I. Local Java CLI
## Prerequisites

* Maven
* RabbitMQ

### Download and Build
You can skip this step if you use the jar I uploaded.

```
git clone https://github.com/komushi/spring-cloud-stream-sink-stomp.git
cd spring-cloud-stream-sink-stomp
mvn clean package
```

### Start RabbitMQ

```
rabbitmq-server
```

### 1. Test with Time Source

Start Local Stomp Sink using RabbitMQ

```
java -jar target/spring-cloud-stream-sink-stomp-0.0.1-SNAPSHOT.jar --server.port=8080 --stomp.withSockJS=true --spring.cloud.stream.bindings.input.destination=ticktock --logging.level.io.pivotal.spring.cloud.stream.sink=TRACE
```

Start Time Source using RabbitMQ

https://rawgit.com/komushi/spring-cloud-stream/master/time-source-rabbit-1.0.0.BUILD-SNAPSHOT.jar

```
java -jar time-source-rabbit-1.0.0.BUILD-SNAPSHOT.jar --server.port=7070 --spring.cloud.stream.bindings.output.destination=ticktock
```

Got the following log.

```
2016-06-27 16:29:58.775 TRACE 25312 --- [L-AIc7vmDDzmg-1] i.p.s.c.s.sink.StompSinkConfiguration    : Handling message: GenericMessage [payload=06/27/16 16:29:58, headers={amqp_receivedRoutingKey=ticktock, amqp_receivedExchange=ticktock, amqp_deliveryTag=1, amqp_consumerQueue=ticktock.anonymous.1RHE-1zOSL-AIc7vmDDzmg, amqp_redelivered=false, id=1380c75f-1359-2f0d-ed60-102ea4b0bd64, amqp_consumerTag=amq.ctag-NOaGrv2w5DCtsuQ1_AXymg, contentType=text/plain, timestamp=1467012598773}]
2016-06-27 16:29:59.769 TRACE 25312 --- [L-AIc7vmDDzmg-1] i.p.s.c.s.sink.StompSinkConfiguration    : Handling message: GenericMessage [payload=06/27/16 16:29:59, headers={amqp_receivedRoutingKey=ticktock, amqp_receivedExchange=ticktock, amqp_deliveryTag=2, amqp_consumerQueue=ticktock.anonymous.1RHE-1zOSL-AIc7vmDDzmg, amqp_redelivered=false, id=f4ac9e56-320c-2534-186b-64a7969d1f61, amqp_consumerTag=amq.ctag-NOaGrv2w5DCtsuQ1_AXymg, contentType=text/plain, timestamp=1467012599768}]
2016-06-27 16:30:00.774 TRACE 25312 --- [L-AIc7vmDDzmg-1] i.p.s.c.s.sink.StompSinkConfiguration    : Handling message: GenericMessage [payload=06/27/16 16:30:00, headers={amqp_receivedRoutingKey=ticktock, amqp_receivedExchange=ticktock, amqp_deliveryTag=3, amqp_consumerQueue=ticktock.anonymous.1RHE-1zOSL-AIc7vmDDzmg, amqp_redelivered=false, id=ada64e2d-fd90-505b-314b-e5509b226ec7, amqp_consumerTag=amq.ctag-NOaGrv2w5DCtsuQ1_AXymg, contentType=text/plain, timestamp=1467012600773}]
```

### 2. Test with Http Source

Start Local StompSink using RabbitMQ

```
java -jar target/spring-cloud-stream-sink-stomp-0.0.1-SNAPSHOT.jar --server.port=8080 --stomp.topic=httpjson --stomp.withSockJS=true --spring.cloud.stream.bindings.input.destination=httpjson --logging.level.io.pivotal.spring.cloud.stream.sink=TRACE
```

Start HttpSource using RabbitMQ

https://rawgit.com/komushi/spring-cloud-stream/master/http-source-rabbit-1.0.0.BUILD-SNAPSHOT.jar

```
java -jar http-source-rabbit-1.0.0.BUILD-SNAPSHOT.jar --server.port=7070 --spring.cloud.stream.bindings.output.destination=httpjson
```

Curl POST

```
curl -H "Content-Type: application/json" -X POST -d'abctest' 'http://localhost:7070'
```

```
2016-06-28 15:03:07.049 TRACE 58483 --- [wSkCSHQR6F3dg-1] i.p.s.c.s.sink.StompSinkConfiguration    : Handling message: GenericMessage [payload=abctest, headers={amqp_receivedRoutingKey=httpjson, amqp_receivedExchange=httpjson, amqp_deliveryTag=2, amqp_consumerQueue=httpjson.anonymous.I6MUGzg0SwSkCSHQR6F3dg, amqp_redelivered=false, id=efe9a0eb-8da6-ba31-3fd7-dc52529149e0, amqp_consumerTag=amq.ctag-pQRvXpUotbdYd7XxZ8QdcA, contentType=application/json, timestamp=1467093787049}]
```

```
curl -H "Content-Type: application/json" -X POST -d'[{"rank":1,"from":"C167.155","to":"C135.295","count":549},{"rank":2,"from":"C246.86","to":"C14.174","count":548},{"rank":3,"from":"C134.297","to":"C78.17","count":547},{"rank":4,"from":"C78.147","to":"C173.81","count":546},{"rank":5,"from":"C34.197","to":"C156.151","count":545},{"rank":6,"from":"C87.65","to":"C222.56","count":544},{"rank":7,"from":"C172.8","to":"C29.116","count":543},{"rank":8,"from":"C282.175","to":"C82.193","count":542},{"rank":9,"from":"C248.103","to":"C248.26","count":541},{"rank":10,"from":"C212.278","to":"C299.203","count":540}]' 'http://localhost:7070'
```

```
2016-06-28 15:02:28.345 TRACE 58483 --- [wSkCSHQR6F3dg-1] i.p.s.c.s.sink.StompSinkConfiguration    : Handling message: GenericMessage [payload=[{"rank":1,"from":"C167.155","to":"C135.295","count":549},{"rank":2,"from":"C246.86","to":"C14.174","count":548},{"rank":3,"from":"C134.297","to":"C78.17","count":547},{"rank":4,"from":"C78.147","to":"C173.81","count":546},{"rank":5,"from":"C34.197","to":"C156.151","count":545},{"rank":6,"from":"C87.65","to":"C222.56","count":544},{"rank":7,"from":"C172.8","to":"C29.116","count":543},{"rank":8,"from":"C282.175","to":"C82.193","count":542},{"rank":9,"from":"C248.103","to":"C248.26","count":541},{"rank":10,"from":"C212.278","to":"C299.203","count":540}], headers={amqp_receivedRoutingKey=httpjson, amqp_receivedExchange=httpjson, amqp_deliveryTag=1, amqp_consumerQueue=httpjson.anonymous.I6MUGzg0SwSkCSHQR6F3dg, amqp_redelivered=false, id=c04687c0-43a2-07a5-05e8-3477379a89cd, amqp_consumerTag=amq.ctag-pQRvXpUotbdYd7XxZ8QdcA, contentType=application/json, timestamp=1467093748342}]
```

You can subscribe http://localhost:8080/stomp at /topic/httpjson to retrieve the inbound messages.

# II. Another deployment option - spring boot in docker

## 1. assembly.xml under src/main/docker
```
<assembly
        xmlns="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.2"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.2 http://maven.apache.org/xsd/assembly-1.1.2.xsd">
    <id>spring-cloud-stream-sink-stomp</id>
    <dependencySets>
        <dependencySet>
            <includes>
                <include>io.pivotal.spring.cloud.stream.sink:spring-cloud-stream-sink-stomp</include>
            </includes>
            <outputDirectory>.</outputDirectory>
            <outputFileNameMapping>spring-cloud-stream-sink-stomp.jar</outputFileNameMapping>
        </dependencySet>
    </dependencySets>
</assembly>
```

## 2. Modify docker-maven-plugin inside pom.xml
### 2.1 Entry Point Shell Mode
Under plugin/configuration/images/image/build/, modify entryPoint like below.
```
...
<entryPoint>
    <shell>java -jar /maven/{project.artifactId}.jar $SCS_OPTS</shell>
</entryPoint>
...
```

### 2.2 Entry Point Exec Mode
Under plugin/configuration/images/image/build/, modify entryPoint like below.
```
...
<entryPoint>
    <exec>
        <arg>java</arg>
        <arg>-jar</arg>
        <arg>/maven/{project.artifactId}.jar</arg>
    </exec>
</entryPoint>
...
```

## 3. Build docker image by maven
```
mvn clean package docker:build
```

if necessay push to docker hub

```
mvn docker:push -Ddocker.username=[provide your username] -Ddocker.password=[provide password]
```

## 4. Run the image

Since we use docker container here and the Spring Cloud Stream App deault port is 8080 so the --server.port(the container port) parameter does not need to be specified for docker deployment.

### 4.1 Entry Point Shell Mode

```
docker run -e SCS_OPTS='<app_args>' -p <host_port>:<container_port> komushi/spring-cloud-stream-sink-stomp
```

```
docker run -e SCS_OPTS='--stomp.withSockJS=true --spring.rabbitmq.host=192.168.11.111 --spring.rabbitmq.port=5672 --spring.rabbitmq.username=test --spring.rabbitmq.password=test' -p 8888:8080 komushi/spring-cloud-stream-sink-stomp
```

### 4.2 Entry Point Exec Mode

```
docker run -p <host_port>:<container_port> komushi/spring-cloud-stream-sink-stomp <app_args>
```

```
docker run -p 8888:8080 komushi/spring-cloud-stream-sink-stomp --stomp.withSockJS=true --spring.rabbitmq.host=192.168.11.111 --spring.rabbitmq.port=5672 --spring.rabbitmq.username=test --spring.rabbitmq.password=test
```

# TODO: use Full-Featured Broker - External Broker such as RabbitMQ
Reference:
http://docs.spring.io/spring/docs/current/spring-framework-reference/html/websocket.html#websocket-stomp-handle-broker-relay
https://github.com/bijukunjummen/spring-websocket-chat-sample
