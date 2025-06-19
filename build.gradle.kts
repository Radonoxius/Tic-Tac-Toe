plugins {
    id("application")
    id("java")
}

group = "ix.radon"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("ix.radon.game.Main")

    applicationDefaultJvmArgs = listOf(
        "-Xlint:preview",
        "--enable-preview",
        "--enable-native-access=ALL-UNNAMED"
    )
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
