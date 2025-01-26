# Dockerfile
FROM openjdk:17-jdk-slim AS build
COPY . .
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-slim
EXPOSE 8080
COPY --from=build target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]