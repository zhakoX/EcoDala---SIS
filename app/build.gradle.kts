import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}

// Local configuration
val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { load(it) }
    }
}

val keystoreProperties = Properties().apply {
    val file = rootProject.file("keystore.properties")
    if (file.exists()) {
        file.inputStream().use { load(it) }
    }
}

fun secret(name: String): String? {
    return localProperties.getProperty(name)
        ?: keystoreProperties.getProperty(name)
        ?: System.getenv(name)
}

val releaseSigningReady = listOf(
    "ECODALA_RELEASE_STORE_FILE",
    "ECODALA_RELEASE_STORE_PASSWORD",
    "ECODALA_RELEASE_KEY_ALIAS",
    "ECODALA_RELEASE_KEY_PASSWORD"
).all { !secret(it).isNullOrBlank() }

android {
    namespace = "com.ecodala"

    compileSdk = 37
    buildToolsVersion = "37.0.0"

    defaultConfig {
        applicationId = "com.ecodala"

        minSdk = 24
        targetSdk = 37

        versionCode = 4
        versionName = "2.0.2-sis4"
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    signingConfigs {
        if (releaseSigningReady) {
            create("release") {
                storeFile = file(
                    secret("ECODALA_RELEASE_STORE_FILE").orEmpty()
                )
                storePassword = secret(
                    "ECODALA_RELEASE_STORE_PASSWORD"
                )
                keyAlias = secret(
                    "ECODALA_RELEASE_KEY_ALIAS"
                )
                keyPassword = secret(
                    "ECODALA_RELEASE_KEY_PASSWORD"
                )
            }
        }
    }

    buildTypes {
        debug {
            buildConfigField(
                "Boolean",
                "ECODALA_LOGGING_ENABLED",
                "true"
            )
        }

        release {
            buildConfigField(
                "Boolean",
                "ECODALA_LOGGING_ENABLED",
                "false"
            )

            if (releaseSigningReady) {
                signingConfig = signingConfigs.getByName("release")
            }

            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(
            org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
        )
    }
}

dependencies {

    // Java API desugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")

    // Jetpack Compose
    implementation(
        platform("androidx.compose:compose-bom:2026.09.00")
    )

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    // AndroidX Activity
    implementation(
        "androidx.activity:activity-compose:1.13.0"
    )

    // AndroidX AppCompat
    implementation(
        "androidx.appcompat:appcompat:1.8.0"
    )

    // Lifecycle / MVVM
    implementation(
        "androidx.lifecycle:lifecycle-viewmodel-compose:2.11.0"
    )

    implementation(
        "androidx.lifecycle:lifecycle-runtime-compose:2.11.0"
    )

    // Navigation
    implementation(
        "androidx.navigation:navigation-compose:2.10.2"
    )

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.3.0")

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.11.0")

    // OpenStreetMap
    implementation(
        "org.osmdroid:osmdroid-android:6.1.20"
    )

    // Unit Testing
    testImplementation(
        "junit:junit:4.13.2"
    )

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.11.0")

    // Debug Tools
    debugImplementation(
        "androidx.compose.ui:ui-tooling"
    )

    debugImplementation(
        "androidx.compose.ui:ui-test-manifest"
    )
}