FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
COPY build/libs/*.jar ApigatewayService.jar
ENTRYPOINT ["java", "-jar", "ApigatewayService.jar"]