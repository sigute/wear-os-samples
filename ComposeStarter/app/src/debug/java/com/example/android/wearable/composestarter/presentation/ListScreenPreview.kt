@file:OptIn(ExperimentalHorologistApi::class)

package com.example.android.wearable.composestarter.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.ui.tooling.preview.WearPreviewLargeRound
import com.google.accompanist.testharness.TestHarness
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.AppScaffold

@WearPreviewLargeRound
@Composable
fun ListScreenResponsivePreview() {
    ResponsivePreview {
        AppScaffold {
            ListScreen("Preview ${it.width.value.toInt()}x${it.height.value.toInt()}")
        }
    }
}

@Composable
fun ResponsivePreview(
    dpPreviewState: DpPreviewState = rememberDpPreviewState(),
    content: @Composable (DpSize) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        val dpSize = dpPreviewState.dpSize
        TestHarness(size = dpSize) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.background)
                    .fillMaxSize()
            ) {
                content(dpSize)
            }
        }
        Row(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .clickable { dpPreviewState.decrement() })
            Box(modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .clickable { dpPreviewState.increment() })
        }
    }
}

@Composable
fun rememberDpPreviewState(): DpPreviewState {
    val width = LocalConfiguration.current.screenWidthDp.dp
    return remember { DpPreviewState(width) }
}

class DpPreviewState(
    initial: Dp,
    val steps: List<Dp> = listOf(192.dp, 213.dp, 227.dp, 233.dp, 240.dp)
) {
    private val dpIndex = mutableIntStateOf(steps.indexOf(initial))

    val dpSize: DpSize
        get() = steps[dpIndex.intValue].let {
            DpSize(it, it)
        }

    fun increment() {
        dpIndex.intValue = (dpIndex.intValue + 1).coerceAtMost(steps.lastIndex)
    }

    fun decrement() {
        dpIndex.intValue = (dpIndex.intValue - 1).coerceAtLeast(0)
    }
}
