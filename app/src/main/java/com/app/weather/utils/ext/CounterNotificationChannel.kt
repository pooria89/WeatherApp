package com.app.weather.utils.ext

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.app.weather.MainActivity
import com.app.weather.R

class CounterNotificationChannel(
    private val context: Context
) {
    companion object {
        const val COUNTERR_CHANNEL_ID = "100"
    }

    fun showNotification(counter: Int) {
        val intent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context, 1, intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        val notification = NotificationCompat.Builder(context, COUNTERR_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_weather)
            .setContentTitle("Increment Counter")
            .setContentText("Counter")
            .setContentIntent(activityPendingIntent)
//            .addAction()
    }

}