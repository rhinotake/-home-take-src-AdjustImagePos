import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
  // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
  implementation("com.fasterxml.jackson.core:jackson-core:2.13.3")

  // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
  implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")

  // https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-kotlin
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")

  testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}