package com.anoniscoding.paniclock.ui.screens.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class LockscreenIntentReceiver: BroadcastReceiver() {
    // Handle actions and display Lockscreen
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_SCREEN_OFF || intent.action == Intent.ACTION_SCREEN_ON || intent.action == Intent.ACTION_BOOT_COMPLETED) {
            startLockscreen(context)
        }
    }

    // Display lock screen
    private fun startLockscreen(context: Context) {
        val mIntent = Intent(context, LockScreenActivity::class.java)
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(mIntent)
    }
}