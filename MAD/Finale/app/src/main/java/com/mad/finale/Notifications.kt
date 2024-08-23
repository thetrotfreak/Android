package com.mad.finale

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowNotification() {
    val context = LocalContext.current
    val channelId = "order"

    val channel = NotificationChannel(channelId, "order", NotificationManager.IMPORTANCE_DEFAULT)

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    notificationManager.createNotificationChannel(channel)

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.trolley)
        .setContentTitle("Order Confirmed!")
        .setContentText("You will be receiving your order soon!")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    notificationManager.notify(0, builder.build())
}