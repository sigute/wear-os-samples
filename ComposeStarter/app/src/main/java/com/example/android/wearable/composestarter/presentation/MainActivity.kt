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
@file:OptIn(ExperimentalHorologistApi::class, ExperimentalWearFoundationApi::class,
    ExperimentalFoundationApi::class
)

package com.example.android.wearable.composestarter.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.AppScaffold
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults.ItemType
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults.listTextPadding
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults.padding
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.google.android.horologist.compose.material.Chip
import com.google.android.horologist.compose.material.ListHeaderDefaults.firstItemPadding
import com.google.android.horologist.compose.material.ResponsiveListHeader
import com.google.android.horologist.compose.pager.PagerScreen

/**
 * Simple "Hello, World" app meant as a starting point for a new project using Compose for Wear OS.
 *
 * Displays a centered [Text] composable and a list built with [Horologist]
 * (https://github.com/google/horologist).
 *
 * Use the Wear version of Compose Navigation. You can carry
 * over your knowledge from mobile and it supports the swipe-to-dismiss gesture (Wear OS's
 * back action). For more information, go here:
 * https://developer.android.com/reference/kotlin/androidx/wear/compose/navigation/package-summary
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    val navController = rememberSwipeDismissableNavController()

    AppScaffold {
        SwipeDismissableNavHost(
            navController = navController,
            startDestination = "Home"
        ) {
            composable(route = "Home") {
                HomeScreen(onSettingsClick = {
                    navController.navigate("Settings")
                })
            }

            composable(route = "Settings") {
                SettingsScreen()
            }
        }
    }
}

@Composable
fun SettingsScreen() {
    val columnState = rememberResponsiveColumnState(
        contentPadding = padding(
            first = ItemType.Text,
            last = ItemType.Text
        )
    )
    ScreenScaffold(scrollState = columnState) {
        ScalingLazyColumn(columnState = columnState) {

            item {
                ResponsiveListHeader(
                    contentPadding = firstItemPadding(),
                ) {
                    Text("Settings")
                }
            }

            item {
                Text(
                    "Version:\n 2024.05.14-wear-release(242053)",
                    modifier = Modifier.listTextPadding(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun HomeScreen(onSettingsClick: () -> Unit) {
    val pagerState = rememberPagerState(initialPage = 2) { 3 }

    PagerScreen(state = pagerState) { page ->
        when (page) {
            2 -> LibraryScreen(onSettingsClick)
            else -> BlankPage(page)
        }
    }
}

@Composable
fun LibraryScreen(onSettingsClick: () -> Unit) {
    val columnState = rememberResponsiveColumnState(
        contentPadding = padding(
            first = ItemType.SingleButton,
            last = ItemType.Chip
        )
    )
    ScreenScaffold(scrollState = columnState) {
        ScalingLazyColumn(columnState = columnState) {
            item {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(ChipDefaults.SmallIconSize)
                )
            }
            item {
                Text(
                    "Connect to view your library here",
                    modifier = Modifier.listTextPadding()
                )
            }
            item {
                Chip(
                    "Settings",
                    onClick = onSettingsClick,
                )
            }
        }
    }
}

@Composable
fun BlankPage(page: Int) {
    val columnState = rememberResponsiveColumnState(
        contentPadding = padding(
            first = ItemType.Text,
            last = ItemType.Text
        )
    )
    ScreenScaffold(scrollState = columnState) {
        ScalingLazyColumn(columnState = columnState) {
            item {
                Text(
                    "This page intentionally left blank",
                    modifier = Modifier.listTextPadding()
                )
            }
        }
    }
}
