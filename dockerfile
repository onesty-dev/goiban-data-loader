FROM openjdk:8-jdk

MAINTAINER onesty


ARG JAR_FILE=target/loader-0.0.2-SNAPSHOT.jar
ADD ${JAR_FILE} loader.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/loader.jar"]