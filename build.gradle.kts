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
        languageVersion = JavaLanguageVersion.of(21)
    }

    tasks.withType<JavaCompile>().configureEach {
        options.compilerArgs.add("--enable-preview")
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "ix.radon.game.Main"
    }
}

dependencies {
}