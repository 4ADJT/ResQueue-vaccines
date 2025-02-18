FROM maven:3.9.4-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml /app/
COPY src /app/src

RUN mvn -B package -DskipTests || true

RUN mvn -B package -DskipTests

FROM amazoncorretto:21 AS runner

ENV SPRING_PROFILES_ACTIVE=prod

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]