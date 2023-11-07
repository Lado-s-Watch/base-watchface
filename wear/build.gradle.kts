plugins {
    id("basewatchface.primitive.androidapplication")
    id("basewatchface.primitive.android.kotlin")
    id("basewatchface.primitive.android.wear")
    id("basewatchface.primitive.android.hilt")
    id("basewatchface.primitive.detekt")
}

android {
    namespace = "com.dotsdev.basewatchface"

    defaultConfig {
        applicationId = "com.dotsdev.basewatchface"
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
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.uiWear)
    implementation(projects.core.preview)
    implementation(libs.androidxActivityActivityCompose)
    implementation(libs.androidxLifecycleLifecycleRuntimeKtx)
    implementation(libs.gms.service.wearable)
    wearApp(projects.wear)
}