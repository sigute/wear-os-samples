/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.simpledigital

import android.app.Instrumentation
import android.app.UiAutomation
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SimpleDigitalTest {
    private lateinit var instrumentation: Instrumentation
    private lateinit var uiAutomation: UiAutomation
    private lateinit var device: UiDevice

    @Before
    fun setup() {
        instrumentation = InstrumentationRegistry.getInstrumentation()
        uiAutomation = instrumentation.uiAutomation
        device = UiDevice.getInstance(instrumentation)
    }

    @After
    fun after() {
        device.wakeUp()
        device.setWatchface(DefaultWatchFace)
    }

    @Test
    fun watchfaceInteractive() {
        device.setWatchface(WatchfaceId)

        device.wakeUp()

        Thread.sleep(5000)
    }

    @Test
    fun watchfaceAmbient() {
        device.setWatchface(WatchfaceId)

        device.sleep()

        Thread.sleep(5000)
    }

    companion object {
        val WatchfaceId = "com.example.simpledigital"
    }
}
