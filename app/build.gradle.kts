import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { input -> load(input) }
    }
}

val googleWebClientId = "628188239936-0m8j5kh9hht76t5edo86mkfij7f6s5t2.apps.googleusercontent.com"
val debugApiBaseUrl = "http://172.27.39.149:5000"
val productionApiBaseUrl = "https://dsa-visualizer-api-nu.vercel.app"
val remoteApiBaseUrl = (
    localProperties.getProperty("ANDROID_REMOTE_API_BASE_URL")
        ?: providers.gradleProperty("ANDROID_REMOTE_API_BASE_URL").orNull
        ?: providers.environmentVariable("ANDROID_REMOTE_API_BASE_URL").orNull
        ?: ""
).trim()

fun escapedForGradle(value: String): String = value.replace("\\", "\\\\").replace("\"", "\\\"")

android {
    namespace = "com.dsavisualizer.app"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.dsavisualizer.app"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "GOOGLE_CLIENT_ID", "\"${escapedForGradle(googleWebClientId)}\"")
        buildConfigField("String", "REMOTE_API_BASE_URL", "\"${escapedForGradle(remoteApiBaseUrl)}\"")
        manifestPlaceholders["usesCleartextTraffic"] = "false"
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_BASE_URL", "\"${escapedForGradle(debugApiBaseUrl)}\"")
        }
        release {
            isMinifyEnabled = false
            buildConfigField("String", "API_BASE_URL", "\"${escapedForGradle(productionApiBaseUrl)}\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)

    // Google Sign-In
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("androidx.credentials:credentials:1.2.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.2.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")
    implementation(libs.viewpager2)
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
