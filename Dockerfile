FROM maven:3.9-eclipse-temurin-17

WORKDIR /app

CMD ["mvn", "spring-boot:run"]