FROM maven:3.9.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean
RUN package -DskipTests -X

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar ./banking-api.jar
EXPOSE 8080
CMD ["java", "-jar", "banking-api.jar"]