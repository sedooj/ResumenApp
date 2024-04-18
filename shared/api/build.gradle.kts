plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    kotlin("plugin.serialization") version "1.9.22"
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.sedooj.api"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Hilt
    implementation(libs.dagger)
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)

    implementation(project(":feature:localstorage"))

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

    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

kapt {
    correctErrorTypes = true
}