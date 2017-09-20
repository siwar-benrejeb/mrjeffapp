FROM openjdk:8-jdk-alpine

ENV JAVA_OPTS=""

ADD target/product-service-0.0.1-SNAPSHOT.jar product-service.jar

RUN sh -c 'touch /product-service.jar'

EXPOSE 8084

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /product-service.jar" ]