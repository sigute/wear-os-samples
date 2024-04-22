package com.example.simpledigital

import android.app.Activity
import android.app.UiAutomation
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import androidx.test.uiautomator.UiDevice
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun Context.setWatchface(watchfaceName: ComponentName) {
    val intent = Intent("com.google.android.wearable.app.DEBUG_SURFACE").apply {
        putExtra("operation", "set-watchface")
        putExtra("component", watchfaceName)
    }
    submitBroadcast(intent) {
        println("BUT result")
        println(it)
        println(it.extras?.keySet()?.toList())
        println(it.extras?.getString("operation"))
        println(it.dataString)
        println(it.flags)
    }
}

suspend fun Context.setWatchface(watchFaceId: String) {
    val intent = Intent("com.google.android.wearable.app.DEBUG_SURFACE").apply {
        putExtra("operation", "set-watchface")
        putExtra("watchFaceId", watchFaceId)
    }
    submitBroadcast(intent) {
        println("BUT result")
        println(it)
        println(it.extras?.keySet()?.toList())
        println(it.extras?.getString("operation"))
        println(it.dataString)
        println(it.flags)
    }
}

suspend fun Context.currentWatchface(): ComponentName {
    val intent = Intent("com.google.android.wearable.app.DEBUG_SURFACE").apply {
        putExtra("operation", "current-watchface")
    }
    return submitBroadcast(intent) {
        println("BUT result")
        println(it)
        println(it.extras?.keySet()?.toList())
        println(it.extras?.getString("operation"))
        println(it.dataString)
        println(it.flags)
        ComponentName("", "")
    }
}

suspend fun <T> Context.submitBroadcast(intent: Intent, handleResult: (Intent) -> T): T {
    return suspendCancellableCoroutine { cont ->
        try {
            sendOrderedBroadcast(
                intent, null, object : BroadcastReceiver() {
                    override fun onReceive(context: Context, intent: Intent) {
                        try {
                            cont.resume(handleResult(intent))
                        } catch (e: Exception) {
                            cont.resumeWithException(e)
                        }

                    }
                }, null, Activity.RESULT_OK, null, null
            )
        } catch (e: Exception) {
            cont.resumeWithException(e)
        }
    }
}

@Suppress("UNUSED_VARIABLE")
fun UiDevice.setWatchface(watchfaceName: ComponentName) {
    // From https://cs.android.com/android-studio/platform/tools/base/+/mirror-goog-studio-main:deploy/deployer/src/main/java/com/android/tools/deployer/model/component/WatchFace.java
    val result =
        executeShellCommand("am broadcast -a com.google.android.wearable.app.DEBUG_SURFACE --es operation set-watchface --ecn component ${watchfaceName.flattenToString()}")

    // TODO error checking
}

@Suppress("UNUSED_VARIABLE")
fun UiDevice.setWatchface(watchFaceId: String) {
    val result =
        executeShellCommand("am broadcast -a com.google.android.wearable.app.DEBUG_SURFACE --es operation set-watchface --ecn watchFaceId $watchFaceId")

    // TODO error checking
}

fun UiDevice.currentWatchface(): String {
    return executeShellCommand("am broadcast -a com.google.android.wearable.app.DEBUG_SURFACE --es operation current-watchface")
}

val DefaultWatchFace = ComponentName(
    "com.google.android.wearable.sysui",
    "com.google.android.clockwork.sysui.experiences.defaultwatchface.DefaultWatchFace"
)

inline fun UiAutomation.withShellPermission(block: () -> Unit) {
    adoptShellPermissionIdentity()

    try {
        block()
    } finally {
        dropShellPermissionIdentity()
    }
}

fun UiDevice.pressSleep() {
    pressKeyCode(KeyEvent.KEYCODE_SLEEP)
}

fun UiDevice.pressWakeup() {
    pressKeyCode(KeyEvent.KEYCODE_WAKEUP)
}
