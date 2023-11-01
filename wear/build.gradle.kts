plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.dotsdev.basewatchface"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.dotsdev.basewatchface"
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.core.ui)
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("com.google.android.gms:play-services-wearable:18.1.0")
    implementation("androidx.percentlayout:percentlayout:1.0.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation(platform(libs.composeBom))
    implementation(libs.composeUi)
    implementation(libs.composeUiToolingPreview)
    implementation("androidx.wear.compose:compose-material:1.0.0")
    implementation("androidx.wear.compose:compose-foundation:1.0.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation(libs.wear.watchface.complications.data)
    implementation(libs.wear.watchface.complications.data.source)
    implementation(libs.wear.watchface)
    implementation(libs.wear.watchface.client)
    implementation(libs.wear.watchface.complications.rendering)
    implementation(libs.wear.watchface.data)
    implementation(libs.wear.watchface.editor)
    implementation(libs.wear.watchface.style)
    androidTestImplementation(platform(libs.composeBom))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation(libs.composeUiTooling)
    debugImplementation(libs.composeUiTestManifest)
    wearApp(projects.wear)
}