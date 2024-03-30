plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    kotlin("plugin.serialization") version "1.9.22"
    id("androidx.room") version "2.6.1" apply false
}

android {
    namespace = "com.sedooj.architecture"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
    // Room
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)

    // Network
    implementation(libs.kotlinx.serialization)
    implementation(libs.network.coil)
    implementation(libs.network.ktor.client.core)
    implementation(libs.network.ktor.client.android)
    implementation(libs.network.ktor.client.cio)
    implementation(libs.network.ktor.client.logging)
    implementation(libs.network.ktor.client.negotiation)
    implementation(libs.network.ktor.client.serialization)

    // Coroutines
    implementation(libs.kotlinx.coroutines)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime)


    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}