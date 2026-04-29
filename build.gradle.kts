plugins {
    java
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.sonarqube") version "4.4.1.3373"
    jacoco
    id("info.solidsoft.pitest") version "1.15.0" // 1. Añadimos Pitest de vuelta
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

    // Pruebas de Cucumber y JUnit
    testImplementation("io.cucumber:cucumber-java:7.15.0")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.15.0")
    testImplementation("org.junit.platform:junit-platform-suite")

    // Soporte para pruebas antiguas escritas en JUnit 4 y Cucumber
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.cucumber:cucumber-junit:7.15.0")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
}

// 2. Tarea genérica de pruebas con JaCoCo
tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

// 3. Tarea específica para Pruebas de Aceptación (El Paso 4 que te faltaba)
tasks.register<Test>("acceptanceTest") {
    description = "Ejecuta las pruebas de aceptación (Cucumber)"
    group = "verification"
    useJUnitPlatform()
    // Esto asegura que guarde los reportes en la carpeta que Azure espera
    reports {
        junitXml.required.set(true)
    }
}

// 4. Reporte de Cobertura
tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
}

// 5. Configuración de SonarQube
sonar {
    properties {
        property("sonar.projectKey", "ToDo")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.coverage.jacoco.xmlReportPaths", "${project.layout.buildDirectory.get()}/reports/jacoco/test/jacocoTestReport.xml")
        property("sonar.exclusions", "**/Principal.java, **/Tarea.java")
    }
}

// 6. Configuración básica de Pitest (para que no falle buscando clases)
pitest {
    targetClasses.set(setOf("org.example.*"))
    threads.set(4)
    outputFormats.set(setOf("XML", "HTML"))
}