FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
COPY build/libs/*.jar DiscoveryService.jar
ENTRYPOINT ["java", "-jar", "DiscoveryService.jar"]