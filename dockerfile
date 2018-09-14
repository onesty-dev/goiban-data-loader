FROM openjdk:8-jdk

MAINTAINER onesty

RUN apt-get update \
    && apt-get upgrade -y

COPY ./target/loader-0.0.1-SNAPSHOT.jar loader-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "loader-0.0.1-SNAPSHOT.jar"]