import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.41"
}

group = "neer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compile("com.github.grumlimited:geocalc:0.5.7")
    compile("com.sparkjava:spark-kotlin:1.0.0-alpha")
    compile("com.google.firebase:firebase-admin:6.12.0")
    compile("org.slf4j:slf4j-simple:1.7.21")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}