plugins {
    kotlin("jvm") version "1.9.20"
    `maven-publish`
    application
    id("org.jetbrains.dokka") version "1.9.10"
}

group = "com.github.zap_lib"
version = "0.4.0"

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

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.zap_lib"
            artifactId = "zap-lib"
            version = "0.4.0"

            from(components["kotlin"])
        }
    }
}
