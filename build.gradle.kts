plugins {
    java
    application
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.sonarqube") version "5.0.0.4638"
    id("info.solidsoft.pitest") version "1.15.0"
}

group = "org.example"
version = "1.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

// --- VARIABLES DE VERSIÓN ---
// Centralizamos las versiones aquí para que SonarQube pase el Quality Gate
val junitBomVersion = "5.10.0"
val cucumberVersion = "7.15.0"
val junit4Version = "4.13.2"
val pitestJunitPluginVersion = "1.2.1"

dependencies {

    // --- 1. Todas las implementaciones (implementation) ---
    implementation("org.springframework.boot:spring-boot-starter-web")

    // --- 2. Todas las implementaciones de prueba (testImplementation) ---
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:$junitBomVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-junit:$cucumberVersion")
    testImplementation("junit:junit:$junit4Version")

    // --- 3. Todas las de tiempo de ejecución de prueba (testRuntimeOnly) ---
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine")

}

tasks.test {
    useJUnitPlatform()
    systemProperty("file.encoding", "UTF-8")
}

application {
    mainClass.set("org.example.Principal")
}

sonar {
    properties {
        property("sonar.projectKey", "ToDo")
        property("sonar.projectName", "ToDo")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.token", System.getenv("SONAR_TOKEN"))
    }
}

pitest {
    targetClasses.set(listOf("org.example.*"))
    targetTests.set(listOf("org.example.*Test"))

    // Inyectamos la variable aquí también
    junit5PluginVersion.set(pitestJunitPluginVersion)

    threads.set(4)
    outputFormats.set(listOf("HTML"))
    timestampedReports.set(false)
}

tasks.register<Test>("acceptanceTest") {
    useJUnitPlatform()
    description = "Runs Cucumber acceptance tests."
    group = "verification"
}