FROM openjdk:8-jdk-alpine

ENV JAVA_OPTS=""

ADD target/customer-service-0.0.1-SNAPSHOT.jar customer-service.jar

RUN sh -c 'touch /customer-service.jar'

EXPOSE 8083

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /customer-service.jar" ]