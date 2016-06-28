# StompSink
## For what is the simulator created for?
Data-MicroServices Application provided as Spring Cloud Stream

# 1. Local Java CLI
## Prerequisites

* Maven
* RabbitMQ or Kafka

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

### Start Local StompSink using RabbitMQ

```
java -jar target/StompSink-0.0.1-SNAPSHOT.jar --server.port=8080 --stomp.withSockJS=true --spring.cloud.stream.bindings.input.destination=ticktock --logging.level.io.pivotal.spring.cloud.stream.sink=TRACE
```

### Start Time Source using RabbitMQ

https://rawgit.com/komushi/spring-cloud-stream/master/time-source-rabbit-1.0.0.BUILD-SNAPSHOT.jar

```
java -jar time-source-rabbit-1.0.0.BUILD-SNAPSHOT.jar --server.port=7070 --spring.cloud.stream.bindings.output.destination=ticktock
```

### 
```
2016-06-27 16:29:58.775 TRACE 25312 --- [L-AIc7vmDDzmg-1] i.p.s.c.s.sink.StompSinkConfiguration    : Handling message: GenericMessage [payload=06/27/16 16:29:58, headers={amqp_receivedRoutingKey=ticktock, amqp_receivedExchange=ticktock, amqp_deliveryTag=1, amqp_consumerQueue=ticktock.anonymous.1RHE-1zOSL-AIc7vmDDzmg, amqp_redelivered=false, id=1380c75f-1359-2f0d-ed60-102ea4b0bd64, amqp_consumerTag=amq.ctag-NOaGrv2w5DCtsuQ1_AXymg, contentType=text/plain, timestamp=1467012598773}]
2016-06-27 16:29:59.769 TRACE 25312 --- [L-AIc7vmDDzmg-1] i.p.s.c.s.sink.StompSinkConfiguration    : Handling message: GenericMessage [payload=06/27/16 16:29:59, headers={amqp_receivedRoutingKey=ticktock, amqp_receivedExchange=ticktock, amqp_deliveryTag=2, amqp_consumerQueue=ticktock.anonymous.1RHE-1zOSL-AIc7vmDDzmg, amqp_redelivered=false, id=f4ac9e56-320c-2534-186b-64a7969d1f61, amqp_consumerTag=amq.ctag-NOaGrv2w5DCtsuQ1_AXymg, contentType=text/plain, timestamp=1467012599768}]
2016-06-27 16:30:00.774 TRACE 25312 --- [L-AIc7vmDDzmg-1] i.p.s.c.s.sink.StompSinkConfiguration    : Handling message: GenericMessage [payload=06/27/16 16:30:00, headers={amqp_receivedRoutingKey=ticktock, amqp_receivedExchange=ticktock, amqp_deliveryTag=3, amqp_consumerQueue=ticktock.anonymous.1RHE-1zOSL-AIc7vmDDzmg, amqp_redelivered=false, id=ada64e2d-fd90-505b-314b-e5509b226ec7, amqp_consumerTag=amq.ctag-NOaGrv2w5DCtsuQ1_AXymg, contentType=text/plain, timestamp=1467012600773}]
```

# 2. Local Test with Sring Dataflow Server

## Prerequisites

* Maven
* Spring Cloud Dataflow Shell
* Spring Cloud Dataflow Server - Local
* RabbitMQ or Kafka

## Quick Start

### Download and Build
You can skip this step if you use the jar I uploaded.

```
git clone https://github.com/komushi/spring-cloud-stream-processor-flat2tuple.git
cd spring-cloud-stream-processor-flat2tuple
mvn package
```

### Start RabbitMQ

```
rabbitmq-server
```

### Download the Spring Cloud Data Flow Server and Shell apps

```
wget http://repo.spring.io/snapshot/org/springframework/cloud/spring-cloud-dataflow-server-local/1.0.0.BUILD-SNAPSHOT/spring-cloud-dataflow-server-local-1.0.0.BUILD-SNAPSHOT.jar
wget http://repo.spring.io/snapshot/org/springframework/cloud/spring-cloud-dataflow-shell/1.0.0.BUILD-SNAPSHOT/spring-cloud-dataflow-shell-1.0.0.BUILD-SNAPSHOT.jar
```

### Start Dataflow Server Local using RabbitMQ

```
java -jar spring-cloud-dataflow-server-local-1.0.0.BUILD-SNAPSHOT.jar --binder=rabbit
```

### Start Dataflow Shell

```
java -jar spring-cloud-dataflow-shell-1.0.0.BUILD-SNAPSHOT.jar
```

### Register a custom sink app - stomp in Dataflow Shell

Use the built jar for rabbit

```
app register --name stomp --type sink --uri file:///Users/lei_xu/Desktop/spring-cloud-stream/StompSink-0.0.1-SNAPSHOT.jar
```

Or use the uploaded jar

```
app register --name flat2tuple --type processor --uri https://rawgit.com/komushi/spring-cloud-stream/master/StompSink-0.0.1-SNAPSHOT.jar
```

### Start a stream in Dataflow Shell

```
stream create --name ticktock2stomp --definition "time | stomp --logging.level.io.pivotal.spring.cloud.stream.sink=TRACE" --deploy
```