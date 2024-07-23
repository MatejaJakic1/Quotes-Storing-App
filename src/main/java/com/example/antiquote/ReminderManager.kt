package com.example.antiquote

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

object ReminderManager {
    const val REMINDER_NOTIFICATION_REQUEST_CODE = 123
    fun startReminder(
        context: Context,
        reminderTime: String = "09:00",
        // reminderId is used to identify the alarm in case we want to stop it later
        reminderId: Int = REMINDER_NOTIFICATION_REQUEST_CODE
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


        // AlarmReceiver is being triggered
        val intent = Intent(context.applicationContext, AlarmReceiver::class.java).let {
            // let is some kind of lambda function
                    intent ->
                // pending intent allows the third party function to use your application's permission to execute sth
                PendingIntent.getBroadcast(
                    context.applicationContext,
                    reminderId,
                    intent,
                    //this FLAG_UPDATE_CURRENT means that if PendingIntent exists, we want to update it with new data
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }

        // Defining the precise time for the notification
        val (hours, min) = reminderTime.split(":").map { it.toInt() }
        val calendar: Calendar = Calendar.getInstance(Locale.ENGLISH).apply {
            set(Calendar.HOUR_OF_DAY, hours)
            set(Calendar.MINUTE, min)
        }

        if (Calendar.getInstance(Locale.ENGLISH)
                    // calendar is our time that we want to set,
                    // while Calendar.getInstance is our current local time
                .apply { add(Calendar.MINUTE, 1) }.timeInMillis - calendar.timeInMillis > 0
        ) {
            // if that time has already passed, add one day
            calendar.add(Calendar.DATE, 1)
        }

        // alarmManager has setAlarmClock and cancel functions
        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(calendar.timeInMillis, intent),
            intent
        )
    }

    /*
    fun stopReminder(
        context: Context,
        reminderId: Int = REMINDER_NOTIFICATION_REQUEST_CODE
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context,
                reminderId,
                intent,
                0
            )
        }
        alarmManager.cancel(intent)
    }*/
}