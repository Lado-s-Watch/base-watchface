import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.dotsdev.basewatchface.buildlogic"

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

kotlin {
    jvmToolchain(11)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(libs.bundles.plugins)
    implementation(libs.javaPoet)
}

gradlePlugin {
    plugins {
        // Primitives
        register("androidApplication") {
            id = "basewatchface.primitive.androidapplication"
            implementationClass = "com.dotsdev.basewatchface.primitive.AndroidApplicationPlugin"
        }
        register("android") {
            id = "basewatchface.primitive.android"
            implementationClass = "com.dotsdev.basewatchface.primitive.AndroidPlugin"
        }
        register("androidKotlin") {
            id = "basewatchface.primitive.android.kotlin"
            implementationClass = "com.dotsdev.basewatchface.primitive.AndroidKotlinPlugin"
        }
        register("androidCompose") {
            id = "basewatchface.primitive.android.compose"
            implementationClass = "com.dotsdev.basewatchface.primitive.AndroidComposePlugin"
        }
        register("androidWear") {
            id = "basewatchface.primitive.android.wear"
            implementationClass = "com.dotsdev.basewatchface.primitive.WearApplicationPlugin"
        }
        register("androidHilt") {
            id = "basewatchface.primitive.android.hilt"
            implementationClass = "com.dotsdev.basewatchface.primitive.AndroidHiltPlugin"
        }
        register("androidCrashlytics") {
            id = "basewatchface.primitive.android.crashlytics"
            implementationClass = "com.dotsdev.basewatchface.primitive.AndroidCrashlyticsPlugin"
        }
        register("androidFirebase") {
            id = "basewatchface.primitive.android.firebase"
            implementationClass = "com.dotsdev.basewatchface.primitive.AndroidFirebasePlugin"
        }
        register("androidRoborazzi") {
            id = "basewatchface.primitive.android.roborazzi"
            implementationClass = "com.dotsdev.basewatchface.primitive.AndroidRoborazziPlugin"
        }
        register("kotlinMppKotlinSerialization") {
            id = "basewatchface.primitive.kmp.serialization"
            implementationClass = "com.dotsdev.basewatchface.primitive.KotlinSerializationPlugin"
        }
        register("koverEntryPoint") {
            id = "basewatchface.primitive.kover.entrypoint"
            implementationClass = "com.dotsdev.basewatchface.primitive.KoverEntryPointPlugin"
        }
        register("detekt") {
            id = "basewatchface.primitive.detekt"
            implementationClass = "com.dotsdev.basewatchface.primitive.DetektPlugin"
        }
        register("oss-licenses") {
            id = "basewatchface.primitive.android.osslicenses"
            implementationClass = "com.dotsdev.basewatchface.primitive.OssLicensesPlugin"
        }

        // Conventions
        register("androidFeature") {
            id = "droidkaigi.convention.androidfeature"
            implementationClass = "com.dotsdev.basewatchface.convention.AndroidFeaturePlugin"
        }
    }
}
