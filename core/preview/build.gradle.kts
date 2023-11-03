@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("basewatchface.primitive.android.library")
    id("basewatchface.primitive.android.kotlin")
    id("basewatchface.primitive.android.compose")
    id("basewatchface.primitive.android.wear")
}

android {
    namespace = "com.dotsdev.basewatchface.preview"

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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidxTestExtJunit)
    androidTestImplementation(libs.androidxTestEspressoEspressoCore)
}