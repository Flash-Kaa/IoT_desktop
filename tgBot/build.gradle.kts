plugins {
    application
    kotlin("jvm") version "2.0.0"
}

group = "com.flasska.firstiotlab"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val tgBotVersion = "18.2.2"
    implementation("dev.inmo:tgbotapi.utils:$tgBotVersion")
    implementation("dev.inmo:tgbotapi:$tgBotVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
application {
    mainClass.set("ServerKt")
}