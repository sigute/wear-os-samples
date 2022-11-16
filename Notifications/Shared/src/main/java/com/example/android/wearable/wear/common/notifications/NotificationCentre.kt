package com.example.android.wearable.wear.common.notifications

import android.app.Notification
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.datastore.core.DataStore
import com.example.android.wearable.wear.common.navigation.IntentBuilder
import com.example.android.wearable.wear.wearnotifications.proto.NotificationsProto.InboxNotification
import com.example.android.wearable.wear.wearnotifications.proto.NotificationsProto.PostedNotifications
import com.example.android.wearable.wear.wearnotifications.proto.NotificationsProto.TextNotification
import com.example.android.wearable.wear.wearnotifications.proto.PostedNotificationKt
import com.example.android.wearable.wear.wearnotifications.proto.postedNotification

class NotificationCentre(
    val context: Context,
    val notificationManager: NotificationManagerCompat,
    val postedNotificationsDataStore: DataStore<PostedNotifications>,
    intentBuilder: IntentBuilder
) {
    val textNotificationRenderer =
        TextNotificationRenderer(context, intentBuilder, notificationManager)
    val inboxNotificationRenderer =
        InboxNotificationRenderer(context, intentBuilder, notificationManager)

    fun createChannels() {
        // NotificationChannels are required for Notifications on O (API 26) and above.
        // Adds NotificationChannel to system. Attempting to create an existing notification
        // channel with its original values performs no operation, so it's safe to perform the
        // below sequence.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val textChannel = textNotificationRenderer.buildNotificationChannel()
            notificationManager.createNotificationChannel(textChannel)

            val inboxChannel = inboxNotificationRenderer.buildNotificationChannel()
            notificationManager.createNotificationChannel(inboxChannel)
        }
    }

    suspend fun postTextNotification(textNotification: TextNotification): Int {
        return postNotification({
            this.text = textNotification
        }, textNotificationRenderer, textNotification)
    }

    suspend fun <T> postNotification(
        notificationSetter: PostedNotificationKt.Dsl.(T) -> Unit,
        notificationRenderer: NotificationRenderer<T>,
        notificationData: T
    ): Int {
        var id: Int = -1

        postedNotificationsDataStore.updateData {
            id = it.lastId + 1
            it.toBuilder()
                .addNotification(postedNotification {
                    this.id = id
                    notificationSetter(notificationData)
                })
                .setLastId(id)
                .build()
        }

        val notification = notificationRenderer.buildNotification(id, notificationData)

        postNotification(id, notification)

        return id
    }

    suspend fun clearNotification(id: Int) {
        postedNotificationsDataStore.updateData {
            val index = it.notificationList.indexOfFirst { notification ->
                notification.id == id
            }

            if (index >= 0) {
                it.toBuilder()
                    .removeNotification(index)
                    .build()
            } else {
                it
            }
        }

        notificationManager.cancel(id)
    }

    private fun postNotification(id: Int, notification: Notification) {
        try {
            notificationManager.notify(id, notification)
        } catch (se: SecurityException) {
            // TODO show snackbar
            Log.e("MainViewModel", "Unable to post notification", se)
        }
    }

    suspend fun postInboxNotification(inboxNotification: InboxNotification): Int {
        return postNotification({
            this.inbox = inboxNotification
        }, inboxNotificationRenderer, inboxNotification)
    }
}
