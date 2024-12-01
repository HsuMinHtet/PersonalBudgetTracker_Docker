# Use a Maven image for building the application
FROM maven:3.9.4-openjdk-23 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use a lightweight image for running the application
FROM amazoncorretto:23
WORKDIR /app
COPY target/PersonalBudgetTracker-0.0.1-SNAPSHOT.jar /app
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "PersonalBudgetTracker-0.0.1-SNAPSHOT.jar"]
