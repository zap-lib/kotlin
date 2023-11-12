plugins {
    kotlin("jvm") version "1.9.20"
    `maven-publish`
    application
}

group = "com.github.zap_lib"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}
