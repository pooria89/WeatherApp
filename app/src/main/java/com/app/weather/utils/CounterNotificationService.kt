package com.app.weather.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.app.service.CounterNotificationReciever
import com.app.feature.current.CurrentWeatherActivity
import com.app.weather.R

class CounterNotificationService(
    private val context: Context
) {
    companion object {
        const val COUNTER_CHANNEL_ID = "100"
    }

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(counter: Int) {
        val intent = Intent(context, CurrentWeatherActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context, 1,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val incrementIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, CounterNotificationReciever::class.java),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_weather)
            .setContentTitle("Increment Counter")
            .setContentText("Counter")
            .setContentIntent(activityPendingIntent)
            .addAction(
                R.drawable.ic_weather,
                "Increment",
                incrementIntent
            )
            .build()
        notificationManager.notify(
            1, notification
        )
    }

}