FROM adoptopenjdk/openjdk8:x86_64-ubi-minimal-jre8u242-b08
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]