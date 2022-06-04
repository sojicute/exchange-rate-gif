FROM openjdk:11-jdk-slim

EXPOSE 8080

RUN mkdir /app

COPY build/libs/*.jar /app/exchange-rate-gif.jar

ENTRYPOINT ["java", "-jar", "/app/exchange-rate-gif.jar"]
