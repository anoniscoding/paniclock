package com.anoniscoding.paniclock.ui.screens.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.anoniscoding.paniclock.R


class LockScreenService: Service() {
    private var mReceiver: BroadcastReceiver? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mReceiver = LockscreenIntentReceiver()
    }

    // Register for Lockscreen event intents
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        registerReceiver(mReceiver, filter)
        startForeground()
        return START_STICKY
    }

    // Run service in foreground so it is less likely to be killed by system
    private fun startForeground() {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(applicationContext, "default")
            .setContentTitle(resources.getString(R.string.app_name))
            .setContentText(resources.getString(R.string.app_name))
            .setSmallIcon(R.drawable.ic_app_logo)
            .setContentIntent(null)
            .setOngoing(true)
            .build()

        startForeground(9999, notification)
    }

    // Unregister receiver
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }
}