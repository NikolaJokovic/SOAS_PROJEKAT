FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/users-service-1.0-SNAPSHOT.jar app.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "app.jar"]