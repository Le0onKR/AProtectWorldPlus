plugins {
    id("java")
    id("maven-publish")
    id("com.gradleup.shadow") version "8.3.0"
}

group = "dev.AidenKR.AProtectWorldPlus"
version = "1.0"

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly ("org.projectlombok:lombok:1.18.38")
    annotationProcessor ("org.projectlombok:lombok:1.18.38")
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifactId = "AProtectWorldPlus"
        }
    }
}