FROM openjdk:8-jdk-alpine

ENV JAVA_OPTS=""

ADD target/order-service-0.0.1-SNAPSHOT.jar order-service.jar

RUN sh -c 'touch /order-service.jar'

EXPOSE 8081

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /order-service.jar" ]