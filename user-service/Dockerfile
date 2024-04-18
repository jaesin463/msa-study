FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
COPY build/libs/*.jar UserService.jar
ENTRYPOINT ["java", "-jar", "UserService.jar"]