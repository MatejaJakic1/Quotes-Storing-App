package com.example.antiquote

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // this receiver starts the alarm if the phone is booted
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            ReminderManager.startReminder(context)
        }
    }
}