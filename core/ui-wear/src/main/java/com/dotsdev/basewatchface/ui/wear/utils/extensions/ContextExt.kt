package com.dotsdev.basewatchface.ui.wear.utils.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Service
import android.app.backup.BackupAgent
import android.content.Context
import android.content.ContextWrapper
import android.os.Build.VERSION.SDK_INT

val appCtx: Context get() = internalCtx ?: internalCtxUninitialized()

private fun internalCtxUninitialized(): Nothing {
    val processName = getProcessName()
    val isDefaultProcess = ':' !in processName
    val (cause: String, solutions: List<String>) = when {
        isDefaultProcess -> "App Startup didn't run" to listOf(
            "If App Startup has been disabled, enable it back in the AndroidManifest.xml file of the app.",
            "For other cases, call injectAsAppCtx() in the app's Application subclass in its initializer or in its onCreate function."
        )
        else -> "App Startup is not enabled for non default processes" to listOf(
            "Call injectAsAppCtx() in the app's Application subclass in its initializer or in its onCreate function."
        )
    }
    error(buildString {
        appendLine("appCtx has not been initialized!")
        when (solutions.size) {
            1 -> appendLine("Possible solution: ${solutions.single()}")
            else -> {
                appendLine("$cause. Possible solutions:")
                solutions.forEachIndexed { index, solution ->
                    append(index + 1); append(". "); append(solution)
                }
            }
        }
    })
}

@SuppressLint("StaticFieldLeak")
private var internalCtx: Context? = null

fun Context.canLeakMemory(): Boolean = when (this) {
    is Application -> false
    is Activity, is Service, is BackupAgent -> true
    is ContextWrapper -> if (baseContext === this) true else baseContext.canLeakMemory()
    else -> applicationContext === null
}

@SuppressLint("DiscouragedPrivateApi", "PrivateApi")
private fun getProcessName(): String {
    if (SDK_INT >= 28) return Application.getProcessName()
    val activityThread = Class.forName("android.app.ActivityThread")
    val methodName = "currentProcessName"
    return activityThread.getDeclaredMethod(methodName).invoke(null) as String
}