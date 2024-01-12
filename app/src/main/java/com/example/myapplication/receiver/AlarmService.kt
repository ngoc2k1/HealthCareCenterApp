package com.example.myapplication.receiver

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class AlarmService(private val context: Context) {

    fun setExactAlarm( id: Int,timeInMillis: Long, title: String, content: String) {
        cancelAlarm(context, id)
        setAlarm(timeInMillis, id, title, content)
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun setAlarm(
        timeInMillis: Long,
        id: Int,
        title: String,
        content: String
    ) {
        val alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("id", id)
        intent.putExtra("title", title)
        intent.putExtra("content", content)
        var flag = PendingIntent.FLAG_UPDATE_CURRENT
        flag = flag or PendingIntent.FLAG_IMMUTABLE
        val alarmIntent = PendingIntent.getBroadcast(context, id, intent, flag)
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            alarmIntent
        )
    }

     fun cancelAlarm(
        context: Context,
        id: Int
    ) {
        val alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
         var flag = PendingIntent.FLAG_UPDATE_CURRENT
         flag = flag or PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getBroadcast(context, id, intent, flag)
        alarmManager.cancel(pendingIntent)
    }
}