plugins {
    java
    id("org.springframework.boot") version "3.2.4" // Ajusta a la versión que uses
    id("io.spring.dependency-management") version "1.1.4"
    id("org.sonarqube") version "4.4.1.3373"
    jacoco // <--- 1. Activamos JaCoCo para medir cobertura
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

val junitBomVersion = "5.10.2"

dependencies {
    // Dependencias base
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:$junitBomVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Asegúrate de tener estas para tus pruebas de Cucumber y JUnit
    testImplementation("io.cucumber:cucumber-java:7.15.0")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.15.0")
    testImplementation("org.junit.platform:junit-platform-suite")

    // Soporte para pruebas antiguas escritas en JUnit 4 y Cucumber
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.cucumber:cucumber-junit:7.15.0")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
}

// 2. Configuramos la tarea de test para que genere reportes JaCoCo
tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // Siempre genera el reporte al terminar los tests
}

// 3. Configuramos el formato del reporte (Sonar necesita XML)
tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true) // Para que puedas verlo tú en build/reports/jacoco/test/html/index.html
    }
}

// 4. Configuración de Sonar vinculada a los reportes de JaCoCo
sonar {
    properties {
        property("sonar.projectKey", "ToDo")
        property("sonar.host.url", "http://localhost:9000")
        // No olvides tener tu TOKEN a mano

        // ESTA ES LA LÍNEA MÁGICA: Conecta Sonar con los resultados de JaCoCo
        property("sonar.coverage.jacoco.xmlReportPaths", "${project.buildDir}/reports/jacoco/test/jacocoTestReport.xml")

        // Opcional: Excluye clases que no tienen lógica (como la clase principal) para subir el %
        property("sonar.exclusions", "**/Principal.java, **/Tarea.java")
    }
}