plugins {
    id("java")
}

group = "xyz.slosa"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // GSON
    implementation("com.google.code.gson:gson:2.11.0");
}
