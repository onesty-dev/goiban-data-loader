FROM openjdk:8-jdk

MAINTAINER onesty

COPY ./target/loader.jar loader.jar
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "loader.jar"]