plugins {
    id("java")
}

group = "ix.radon"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(22)
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "ix.radon.game.Main"
    }
}

dependencies {
}