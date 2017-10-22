FROM openjdk:8-jdk-alpine

ENV JAVA_OPTS=""

ADD target/config-service-0.0.1-SNAPSHOT.jar config-service.jar

RUN sh -c 'touch /config-service.jar'

EXPOSE 8888

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /config-service.jar" ]
