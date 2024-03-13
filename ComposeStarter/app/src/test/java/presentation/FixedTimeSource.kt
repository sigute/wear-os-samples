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
package com.example.android.wearable.composestarter.presentation

import androidx.compose.runtime.Composable
<<<<<<<< HEAD:ComposeStarter/app/src/main/java/com/example/android/wearable/composestarter/presentation/ResponsiveFixedSourceTimeText.kt
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
========
>>>>>>>> main:ComposeStarter/app/src/test/java/presentation/FixedTimeSource.kt
import androidx.wear.compose.material.TimeSource

/**
 * Provides a fixed time source for use with [ResponsiveTimeText]
 */
<<<<<<<< HEAD:ComposeStarter/app/src/main/java/com/example/android/wearable/composestarter/presentation/ResponsiveFixedSourceTimeText.kt
@Composable
fun ResponsiveFixedSourceTimeText(modifier: Modifier = Modifier) {
    val height = LocalConfiguration.current.screenHeightDp
    val padding = height * 0.021
    TimeText(
        modifier = modifier,
        contentPadding = PaddingValues(padding.dp),
        timeSource = object : TimeSource {
            override val currentTime: String
                @Composable get() = "10:10"
        }
    )
========
val FixedTimeSource = object : TimeSource {
    override val currentTime: String
        @Composable get() = "10:10"
>>>>>>>> main:ComposeStarter/app/src/test/java/presentation/FixedTimeSource.kt
}
