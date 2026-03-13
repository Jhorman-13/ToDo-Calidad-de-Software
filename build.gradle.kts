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

dependencies {

    // Spring Boot Web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

tasks.test {
    useJUnitPlatform()
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
    junit5PluginVersion.set("1.2.1")

    threads.set(4)
    outputFormats.set(listOf("HTML"))
    timestampedReports.set(false)
}