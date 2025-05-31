plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    //    id("kotlin-kapt")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.mzhadan.app.diploma"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mzhadan.app.diploma"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)

    implementation(libs.lifecycle.viewmodel)
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)

    api(libs.hilt.android)
    ksp(libs.hilt.compiler)

    api(project(":network"))
    api(project(":reader"))
}