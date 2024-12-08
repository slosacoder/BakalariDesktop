plugins {
    id("java")
}

group = "xyz.slosa"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Json library
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.2")
}

// Force Java 21
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
