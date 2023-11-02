plugins {
    id("basewatchface.primitive.androidapplication")
    id("basewatchface.primitive.android.kotlin")
    id("basewatchface.primitive.android.compose")
    id("basewatchface.primitive.android.hilt")
    id("basewatchface.primitive.android.firebase")
    id("basewatchface.primitive.android.crashlytics")
    id("basewatchface.primitive.detekt")
    id("basewatchface.primitive.android.roborazzi")
    id("basewatchface.primitive.android.osslicenses")
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
    implementation(libs.androidxCoreKtx)
    implementation(libs.androidxActivityActivityCompose)
    implementation(libs.androidxLifecycleLifecycleRuntimeKtx)
    implementation(libs.gms.service.wearable)
    implementation(platform(libs.composeBom))
    implementation(libs.composeUi)
    implementation(libs.composeUiToolingPreview)
    implementation(libs.wear.compose.material)
    implementation(libs.wear.compose.foundation)
    implementation(libs.wear.watchface.complications.data)
    implementation(libs.wear.watchface.complications.data.source)
    implementation(libs.wear.watchface)
    implementation(libs.wear.watchface.client)
    implementation(libs.wear.watchface.complications.rendering)
    implementation(libs.wear.watchface.data)
    implementation(libs.wear.watchface.editor)
    implementation(libs.wear.watchface.style)
    androidTestImplementation(platform(libs.composeBom))
    androidTestImplementation(libs.composeUiTestJunit4)
    debugImplementation(libs.composeUiTooling)
    debugImplementation(libs.composeUiTestManifest)
    wearApp(projects.wear)
}