FROM eclipse-temurin:21-jre

WORKDIR /app

COPY build/libs/Sistemadeautenticacion-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]