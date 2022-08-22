package com.app.weather.utils.ext

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.app.weather.R

class CounterNotificationChannel(
    private val context: Context
) {
    companion object {
             const val COUNTERR_CHANNEL_ID = "100"
    }

    fun createNotification(counter: Int) {
        val notification = NotificationCompat.Builder(context, COUNTERR_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_weather)
            .setContentTitle("Increment Counter")
            .setContentText("Counter")

    }

}