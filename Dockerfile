# Etapa 1: Usamos una imagen de Gradle oficial para compilar el código
FROM gradle:8.10-jdk17 AS build
WORKDIR /app
COPY . .
# Usamos assemble en lugar de build para ir directo al grano sin efectos secundarios
RUN ./gradlew assemble --no-daemon

# Etapa 2: Creamos la imagen final súper ligera
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/ToDo-1.0-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]