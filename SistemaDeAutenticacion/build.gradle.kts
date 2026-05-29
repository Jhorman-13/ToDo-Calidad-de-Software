plugins {
    id("java")
    id("org.sonarqube") version "5.0.0.4638"
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    id("info.solidsoft.pitest") version "1.15.0"
    id("jacoco")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.10.0"
val cucumberVersion = "7.18.0"
val junitPlatformVersion = "1.10.2"
val cucumberSpring = "7.14.0"

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.platform:junit-platform-suite:$junitPlatformVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-spring:$cucumberSpring")

    pitest("org.pitest:pitest-junit5-plugin:1.2.1")

}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

sonar {
    properties {
        property ("sonar.java.binaries", "build/classes/java/main")
        property("sonar.projectKey", "SistemaAutenticacion")
        property("sonar.projectName", "Sistemadeautenticacion")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.token", System.getenv("SONAR_TOKEN"))

        property("sonar.qualitygate.wait", "true")

        //property("sonar.junit.reportPaths", "build/test-results/test,build/test-results/acceptanceTest")
        //property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
    }
}

pitest {
    pitestVersion.set("1.15.0")
    junit5PluginVersion.set("1.2.1")
    targetClasses.set(listOf("org.example.*"))
    targetTests.set(listOf("org.example.*"))
    threads.set(4)
    outputFormats.set(listOf("HTML"))
    timestampedReports.set(false)
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Test>("acceptanceTest") {
    useJUnitPlatform()
    description = "Runs Cucumber acceptance tests."
    group = "verification"

    useJUnitPlatform()

    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    systemProperty("cucumber.plugin", "pretty")
}