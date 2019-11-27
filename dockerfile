FROM openjdk:11-jdk

LABEL autor="Chris Wohlbrecht"


ARG JAR_FILE=target/loader-0.0.2-SNAPSHOT.jar
ADD ${JAR_FILE} loader.jar

USER 9000

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/loader.jar"]