FROM openjdk:17-ea-jdk-alpine
MAINTAINER mra
ARG WRK_DIR=/opt/sporty-group-aviation
RUN addgroup -S java && adduser -S java -G java && mkdir -p ${WRK_DIR} && chown -R java:java ${WRK_DIR}
USER java:java
WORKDIR ${WRK_DIR}
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} sporty-group-aviation.jar
ENTRYPOINT exec java $JAVA_OPTS -jar /opt/sporty-group-aviation/sporty-group-aviation.jar