# 1. Məlumat Bazası
FROM amazoncorretto:21-alpine

# 2. JAR faylını konteynerə kopyalayır
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 3. Xidmət üçün uyğun portu təyin edir
EXPOSE 8080

# 4. Tətbiqi başlatmaq üçün komanda
ENTRYPOINT ["java", "-jar", "/app.jar"]
