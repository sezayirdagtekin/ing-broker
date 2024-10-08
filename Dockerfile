
FROM openjdk:21-slim

WORKDIR /app

COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo-app:1.0.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "demo-app:1.0.jar"]