plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrainsKotlinSerialization)
    // Activa Hilt y KSP
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.updavid.liveoci_hilt"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.updavid.liveoci_hilt"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
        buildConfig = true
        resValues = true
    }
    sourceSets {
        getByName("main") {
            assets {
                srcDirs("src\\main\\assets", "src\\main\\assets")
            }
        }
    }
}

try {
    ksp {
        arg("hilt.disableModulesHaveInstallInCheck", "true")
    }
} catch (e: Exception) {
    TODO("Not yet implemented")
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.androidx.material3)
    ksp(libs.room.compiler)
    // viewModel()
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Retrofit
    implementation(libs.com.squareup.retrofit2.retrofit)
    // JSON
    implementation(libs.com.squareup.retrofit2.converter.gson)
    // Coil
    implementation(libs.io.coil.kt.coil.compose)
    // Navigation
    implementation(libs.androidx.navigation.compose)
    // Icons extendend
    implementation(libs.androidx.compose.material.icons.extended)
    // Implementación de Hilt
    implementation(libs.hilt.android)
    // Integración con Jetpack Compose
    implementation(libs.hilt.navigation.compose)
    //DataStore Preferences
    implementation(libs.androidx.datastore.preferences)
    //Serializable
    implementation(libs.kotlinx.serialization.json)
    // KSP
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}