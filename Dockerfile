# Usar una imagen base de Java (estoy asumiendo Java 17, cámbialo si usas otra versión)
FROM eclipse-temurin:17-jdk-alpine

# Crear un directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el .jar que genera Gradle al contenedor
COPY build/libs/ToDo-1.0-SNAPSHOT.jar app.jar

# Exponer el puerto donde corre Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación cuando arranque el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]