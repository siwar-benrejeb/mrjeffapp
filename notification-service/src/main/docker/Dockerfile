FROM openjdk:8u111-alpine

ENV JAVA_OPTS=""

ADD notification-service.jar app.jar

RUN sh -c 'touch /app.jar'
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
