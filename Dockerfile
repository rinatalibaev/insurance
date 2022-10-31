FROM eclipse-temurin:17-jdk-jammy

WORKDIR /home/apps/insurance

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN sed -i 's/\r//' ./mvnw
RUN bash ./mvnw dependency:resolve

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]