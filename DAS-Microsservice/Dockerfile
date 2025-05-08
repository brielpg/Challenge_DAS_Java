FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/email-0.0.1-SNAPSHOT.jar microservice.jar
COPY wait-for.sh wait-for.sh
RUN chmod +x wait-for.sh

EXPOSE 8082

ENTRYPOINT ["./wait-for.sh", "rabbitmq", "java", "-jar", "microservice.jar"]
