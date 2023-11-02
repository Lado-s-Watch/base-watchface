plugins {
    id("basewatchface.primitive.androidapplication")
    id("basewatchface.primitive.android.kotlin")
    id("basewatchface.primitive.android.compose")
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