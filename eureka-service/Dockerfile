FROM openjdk:8-jdk-alpine

ENV JAVA_OPTS=""

ADD target/eureka-service-0.0.1-SNAPSHOT.jar eureka-service.jar

RUN sh -c 'touch /config-service.jar'

EXPOSE 8761

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /eureka-service.jar" ]