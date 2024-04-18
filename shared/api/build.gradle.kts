plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    kotlin("plugin.serialization") version "1.9.22"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {

    // Network
    implementation(libs.kotlinx.serialization)
    implementation(libs.network.ktor.client.core)
    implementation(libs.network.ktor.client.cio)
    implementation(libs.network.ktor.client.okkhttp)
    implementation(libs.network.ktor.client.logging)
    implementation(libs.network.ktor.client.negotiation)
    implementation(libs.network.ktor.client.serialization)
    implementation(libs.network.ktor.auth)
    implementation(libs.kotlinx.datetime)
}