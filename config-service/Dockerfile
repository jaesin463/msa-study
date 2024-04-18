FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
COPY keystore/apiEncryption.jks apiEncryption.jks
COPY build/libs/*.jar ConfigService.jar
ENTRYPOINT ["java", "-jar", "ConfigService.jar"]