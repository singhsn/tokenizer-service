# Use an OpenJDK image
#FROM openjdk:21-jdk-slim

FROM --platform=linux/amd64 openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file (make sure your jar is named correctly)
COPY target/*.jar tokenizer-service.jar

# Expose port (Spring Boot default)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "tokenizer-service.jar"]