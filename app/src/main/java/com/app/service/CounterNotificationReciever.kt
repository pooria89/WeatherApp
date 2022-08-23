package com.app.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.weather.model.Counter
import com.app.weather.utils.CounterNotificationService

class CounterNotificationReciever: BroadcastReceiver() {
    override fun onReceive(context: Context, p1: Intent) {
        val service = CounterNotificationService(context = context)
        service.showNotification(++Counter.value)
    }
}