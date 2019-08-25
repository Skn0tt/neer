import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    idea
    kotlin("jvm") version "1.3.41"
    id("com.github.johnrengelman.shadow") version "4.0.4"
    id("com.avast.gradle.docker-compose") version "0.9.4"
}

group = "de.simonknott.neer"
version = "0.0.1"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf(
                "Main-Class" to application.mainClassName
            )
        )
    }
}

dockerCompose {
    useComposeFiles = listOf("docker-compose.yml")
    buildBeforeUp = false
    createNested("dev").apply {
        useComposeFiles = listOf("docker-compose.yml", "docker-compose.dev.yml")
        buildBeforeUp = false
    }
}

val devComposeBuild by tasks
val devComposeUp by tasks
val shadowJar by tasks
devComposeBuild.dependsOn(shadowJar)
devComposeUp.dependsOn(devComposeBuild)

sourceSets {
    create("integrationTest") {
        withConvention(KotlinSourceSet::class) {
            kotlin.srcDir("integrationTest")
            compileClasspath += sourceSets["main"].output + sourceSets["test"].output
            runtimeClasspath += sourceSets["main"].output + sourceSets["test"].output
        }
    }
}

configurations["integrationTestImplementation"].extendsFrom(configurations.implementation)
configurations["integrationTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly)

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")
sourceSets["main"].java.srcDirs("src")
sourceSets["test"].java.srcDirs("test")
sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")

val integrationTest = task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"

    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    shouldRunAfter("test")
    dependsOn(devComposeUp)
    environment("API_BASE_URI", "http://localhost:3000")
}

val check by tasks
check.dependsOn(integrationTest)

configurations["integrationTestCompile"].extendsFrom(configurations["testCompile"])
configurations["integrationTestRuntime"].extendsFrom(configurations["testRuntime"])

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    compile("io.ktor:ktor-server-netty:$ktor_version")
    compile("ch.qos.logback:logback-classic:$logback_version")
    compile("io.ktor:ktor-server-core:$ktor_version")
    compile("io.ktor:ktor-locations:$ktor_version")
    compile("io.ktor:ktor-metrics:$ktor_version")
    compile("io.ktor:ktor-auth:$ktor_version")
    compile("io.ktor:ktor-auth-jwt:$ktor_version")
    compile("io.ktor:ktor-jackson:$ktor_version")
    testCompile("io.ktor:ktor-server-tests:$ktor_version")

    testCompile("io.rest-assured:rest-assured:4.0.0")
    compile("com.github.grumlimited:geocalc:0.5.7")
    compile("com.googlecode.libphonenumber:libphonenumber:8.10.16")
    compile("redis.clients:jedis:3.1.0")
}
