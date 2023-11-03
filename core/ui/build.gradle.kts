plugins {
    id("basewatchface.primitive.android.library")
    id("basewatchface.primitive.android.kotlin")
    id("basewatchface.primitive.android.compose")
    id("basewatchface.primitive.android.wear")
}

android {
    namespace = "com.dotsdev.basewatchface.ui"

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
    implementation(projects.core.preview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidxTestExtJunit)
    androidTestImplementation(libs.androidxTestEspressoEspressoCore)
}