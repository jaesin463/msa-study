FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
COPY build/libs/*.jar CatalogService.jar
ENTRYPOINT ["java", "-jar", "CatalogService.jar"]