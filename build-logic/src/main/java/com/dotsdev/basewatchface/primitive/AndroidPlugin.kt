package com.dotsdev.basewatchface.primitive

import com.dotsdev.basewatchface.primitive.androidLibrary
import com.dotsdev.basewatchface.primitive.setupAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class AndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }

            androidLibrary {
                setupAndroid()
            }
        }
    }
}
