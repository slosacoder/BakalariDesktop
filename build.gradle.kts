plugins {
    id("java")
}

group = "xyz.slosa"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
}

// Force Java 21
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}