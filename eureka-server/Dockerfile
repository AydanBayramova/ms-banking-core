# Spring Boot üçün baz imic
FROM amazoncorretto:21-alpine

# Tətbiqi konteynerə köçürün
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Eureka üçün port
EXPOSE 8761

# Eureka Serveri işə sal
ENTRYPOINT ["java", "-jar", "/app.jar"]
