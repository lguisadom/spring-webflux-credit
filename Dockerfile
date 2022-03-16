FROM adoptopenjdk/openjdk11:alpine-slim
ADD target/spring-webflux-credit-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/app.jar"]