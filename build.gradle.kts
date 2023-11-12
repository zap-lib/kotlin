plugins {
    kotlin("jvm") version "1.9.20"
    `maven-publish`
    application
}

group = "com.github.zap_lib"
version = "0.0.2"

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
            version = "0.0.1"

            from(components["kotlin"])
        }
    }
}

application {
    mainClass.set("MainKt")
}
