FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/tokenizer-service.jar tokenizer-service.jar
ENTRYPOINT ["java", "-jar", "tokenizer-service.jar"]
