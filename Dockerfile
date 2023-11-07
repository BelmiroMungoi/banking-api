FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar ./banking-api.jar
EXPOSE 8080
CMD ["java", "-jar", "banking-api.jar"]