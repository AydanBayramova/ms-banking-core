FROM amazoncorretto:21-alpine
VOLUME /tmp
COPY build/libs/ms-identity-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS="-Xms512m -Xmx512m"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]