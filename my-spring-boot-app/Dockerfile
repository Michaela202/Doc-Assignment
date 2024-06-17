
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

RUN mkdir -p /app

WORKDIR /app

COPY pom.xml /app

COPY src /app/src

RUN mvn -B package --file pom.xml -DskipTests

# Fetch Java
FROM eclipse-temurin:21-jdk-alpine
# Expose port 8080
EXPOSE 8080
# Set a docker volume if you want
VOLUME /backend_volume
# Add the jar file
COPY --from=build /app/target/*.jar java-backend-1.0.jar
# Start the application
ENTRYPOINT ["java", "-jar", "/java-backend-1.0.jar"]