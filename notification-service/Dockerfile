FROM openjdk:8-jdk-alpine

ENV JAVA_OPTS=""

ADD target/notification-service-0.0.1-SNAPSHOT.jar notification-service.jar

RUN sh -c 'touch /notification-service.jar'

EXPOSE 8083

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /notification-service.jar" ]