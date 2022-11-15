/*
Copyright 2016 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.example.android.wearable.wear.wearnotifications.handlers

import android.app.NotificationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.wear.compose.material.Text
import com.example.android.wearable.wear.wearnotifications.R
import com.example.android.wearable.wear.wearnotifications.main.StandaloneMainActivity
import com.google.android.horologist.compose.layout.fillMaxRectangle

@Composable
public fun BigTextMainScreen() {
    Box(modifier = Modifier.fillMaxRectangle(), contentAlignment = Alignment.Center) {
        Text(text = stringResource(id = R.string.main_text_activity_big_text_main))
    }

    val context = LocalContext.current
    SideEffect {
        // Cancel Notification
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancel(StandaloneMainActivity.NOTIFICATION_ID)

        // TODO: Handle and display reminder from your database
    }
}
