package com.example.myapplication.receiver
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val id: Int = p1!!.getIntExtra("id", 0)
        var title: String? = p1.getStringExtra("title")
        var content: String? = p1.getStringExtra("content")
        if (title == null) {
            title = "title"
        }
        if (content == null) {
            content = "content"
        }
        sendNormalNotification(p0, id, title, content)
    }
    private fun getBitmapFromVectorDrawable(context: Context?, drawableId: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(context!!, drawableId)!!
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private fun sendNormalNotification(context: Context?, id: Int, title: String, message: String) {
        val mNotifyMgr = context!!.getSystemService(Context.NOTIFICATION_SERVICE)
        val intent = Intent()
        var flag = PendingIntent.FLAG_UPDATE_CURRENT
        flag = flag or PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getActivity(context, id, intent, flag)
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, "health_reminder")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(
                    getBitmapFromVectorDrawable(
                        context,
                        R.mipmap.ic_launcher
                    )
                )
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                .setContentText(message)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
        builder.setDefaults(Notification.DEFAULT_ALL)

        createNormalChanel(mNotifyMgr as NotificationManager)
        val notification = builder.build()
        mNotifyMgr.notify(id, notification)
    }

    private fun createNormalChanel(manager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "health_reminder",
                "Reminder",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableLights(true)
            channel.enableVibration(true)
            channel.importance = NotificationManager.IMPORTANCE_HIGH
            manager.createNotificationChannel(channel)
        }
    }
}

