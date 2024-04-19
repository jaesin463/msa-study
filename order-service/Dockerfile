FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
COPY build/libs/*.jar OrderService.jar
ENTRYPOINT ["java", "-jar", "OrderService.jar"]