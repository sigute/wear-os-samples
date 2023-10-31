/*
 * Copyright 2022 The Android Open Source Project
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
package com.example.wear.tiles.golden

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.ui.tooling.preview.WearPreviewSquare
import com.example.wear.tiles.R
import com.example.wear.tiles.tools.emptyClickable
import com.google.android.horologist.compose.tools.LayoutRootPreview
import com.google.android.horologist.compose.tools.buildDeviceParameters
import com.google.android.horologist.tiles.images.drawableResToImageResource

@Preview(
    device = "id:wearos_rect",
    backgroundColor = 0xff000000,
    showBackground = true,
    group = "Devices - Small Square",
    showSystemUi = true
)
annotation class WearPreviewRect

@WearPreviewRect
@WearPreviewSquare
@Composable
fun Goal() {
    val context = LocalContext.current
    LayoutRootPreview(
        Goal.layout(context, context.deviceParams(), steps = 5168, goal = 8000)
    )
}

@WearPreviewRect
@Composable
fun WorkoutButtons() {
    val context = LocalContext.current
    LayoutRootPreview(
        Workout.buttonsLayout(
            context,
            context.deviceParams(),
            weekSummary = "1 run this week",
            button1Clickable = emptyClickable,
            button2Clickable = emptyClickable,
            button3Clickable = emptyClickable,
            chipClickable = emptyClickable
        )
    ) {
        addIdToImageMapping(
            Workout.BUTTON_1_ICON_ID,
            drawableResToImageResource(R.drawable.ic_run_24)
        )
        addIdToImageMapping(
            Workout.BUTTON_2_ICON_ID,
            drawableResToImageResource(R.drawable.ic_yoga_24)
        )
        addIdToImageMapping(
            Workout.BUTTON_3_ICON_ID,
            drawableResToImageResource(R.drawable.ic_cycling_24)
        )
    }
}

@WearPreviewRect
@Composable
fun WorkoutLargeChip() {
    val context = LocalContext.current
    LayoutRootPreview(
        Workout.largeChipLayout(
            context,
            context.deviceParams(),
            clickable = emptyClickable,
            lastWorkoutSummary = "Last session 45m"
        )
    )
}

@WearPreviewRect
@Composable
fun Run() {
    val context = LocalContext.current
    LayoutRootPreview(
        Run.layout(
            context,
            context.deviceParams(),
            lastRunText = "2 days ago",
            startRunClickable = emptyClickable,
            moreChipClickable = emptyClickable
        )
    )
}

@WearPreviewRect
@Composable
fun Ski() {
    val context = LocalContext.current
    LayoutRootPreview(
        Ski.layout(
            context,
            stat1 = Ski.Stat("Max Spd", "46.5", "mph"),
            stat2 = Ski.Stat("Distance", "21.8", "mile")
        )
    )
}

@WearPreviewRect
@Composable
fun SleepTracker() {
    // TODO: This tile doesn't use standard components; we can achieve it by drawing on a Canvas (Compose's DrawScope) then converting it to a bitmap using Horologist
}

fun Context.deviceParams() = buildDeviceParameters(resources)
