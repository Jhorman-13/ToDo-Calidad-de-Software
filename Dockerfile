
FROM eclipse-temurin:17-jdk-alpine

# Crea un directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el .jar que genera Gradle al contenedor
COPY build/libs/ToDo-1.0-SNAPSHOT.jar app.jar

# Expone el puerto donde corre Spring Boot
EXPOSE 8080

# Ejecuta la aplicación cuando arranque el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]
