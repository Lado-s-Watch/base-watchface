package com.dotsdev.basewatchface.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class WearApplicationPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            android {
                buildFeatures.compose = true
                composeOptions {
                    kotlinCompilerExtensionVersion = libs.version("composeCompiler")
                }
            }
            dependencies {
                implementation(platform(libs.library("composeBom")))
                implementation(libs.library("wear-compose-material"))
                implementation(libs.library("wear-compose-foundation"))
                implementation(libs.library("wear-watchface-complications.data"))
                implementation(libs.library("wear-watchface-complications.data.source"))
                implementation(libs.library("wear.watchface"))
                implementation(libs.library("wear-watchface-client"))
                implementation(libs.library("wear-watchface-complications.rendering"))
                implementation(libs.library("wear-watchface-data"))
                implementation(libs.library("wear-watchface-editor"))
                implementation(libs.library("wear-watchface-style"))
                implementation(libs.library("wear-compose-ui-tooling"))
                implementation(libs.library("wear-compose-navigation"))
            }
        }
    }
}