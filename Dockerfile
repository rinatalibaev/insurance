FROM eclipse-temurin:17-jdk-jammy

WORKDIR /home/apps/insurance

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN apt-get update \
    && apt-get install bash
RUN ./mvnw dependency:resolve

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]