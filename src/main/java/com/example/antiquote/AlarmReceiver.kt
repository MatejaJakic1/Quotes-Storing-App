package com.example.antiquote

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class AlarmReceiver : BroadcastReceiver() {



    override fun onReceive(context: Context, intent: Intent) {
        val quoteNotification = SharedQuotePrefs.getQuotePreference(context)
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        if (quoteNotification != null) {
            notificationManager.sendReminderNotification(
                applicationContext = context,
                channelId = "channel_id",
                quoteToDisplay = quoteNotification
            )
        }
        // Remove this line if you don't want to reschedule the reminder
        ReminderManager.startReminder(context.applicationContext)
    }
}

fun NotificationManager.sendReminderNotification(
    applicationContext: Context,
    channelId: String,
    quoteToDisplay : String
) {

    // this code looks like it opens main activity on click
    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        applicationContext,
        1,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )


    // creating the notification
    val builder = NotificationCompat.Builder(applicationContext, channelId)
        .setContentTitle("Quote of the day")
        .setContentText("Swipe down to read")
        .setSmallIcon(R.drawable.ic_bookmarks)
        .setStyle(NotificationCompat.BigTextStyle().bigText(quoteToDisplay))
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setOngoing(true)
    // notify is a function of NotificationManager
    notify(NOTIFICATION_ID, builder.build())
    SharedQuotePrefs.setShouldISaveQuote(applicationContext, true)
}

const val NOTIFICATION_ID = 1