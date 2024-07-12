plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.android.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.gitem"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.gitem"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        val hostname = "https://api.github.com/"
        val apiKey = ""
        buildConfigField("String", "HOST_NAME", "\"$hostname\"")
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Coil
    implementation(libs.coil.compose)

    // Fonts
    implementation (libs.androidx.ui.text.google.fonts)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.paging.testing.android)
    ksp(libs.hilt.android.compiler)

    // Paging
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.common.android)


    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Lifecycle compose
    implementation(libs.androidx.lifecycle.runtime.compose)

    // retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.retrofit.mock)
    implementation (libs.logging.interceptor)

    //Navigation
    implementation (libs.accompanist.navigation.animation)
    implementation (libs.androidx.navigation.compose)
    implementation (libs.androidx.navigation.runtime)
    implementation(libs.androidx.hilt.navigation.compose)

    // Kotlinx-Serialization
    implementation(libs.kotlinx.serialization.json)

    // Splash
    implementation (libs.androidx.core.splashscreen)

    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.truth)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}