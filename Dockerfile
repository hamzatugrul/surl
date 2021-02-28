FROM openjdk:8-jre-alpine
MAINTAINER Hamza Tugrul Topcuoglu

VOLUME /tmp
ADD target/*.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
