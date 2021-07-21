FROM openjdk:8-jdk-alpine

LABEL MAINTAINER Dhananjay

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} demo.jar

ENTRYPOINT ["java","-jar","/demo.jar"]
