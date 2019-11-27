FROM openjdk:11-jdk

LABEL autor="Chris Wohlbrecht"

ENV TZ=Europe/Berlin

ARG JAR_FILE=target/loader-0.0.3-SNAPSHOT.jar
ADD ${JAR_FILE} loader.jar

USER 9000

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/loader.jar"]
