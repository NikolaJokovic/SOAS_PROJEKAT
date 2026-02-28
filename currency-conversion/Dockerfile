FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/currency-conversion-1.0-SNAPSHOT.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]