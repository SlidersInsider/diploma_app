plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    //    id("kotlin-kapt")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.mzhadan.app.reader"
    compileSdk = 35

    defaultConfig {
        minSdk = 28
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    api(libs.hilt.android)
    ksp(libs.hilt.compiler)
}