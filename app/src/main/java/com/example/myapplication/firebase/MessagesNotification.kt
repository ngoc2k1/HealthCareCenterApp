package com.example.myapplication.firebase

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import androidx.core.content.ContextCompat
import com.example.myapplication.R

class MessagesNotification(base: Context?) : ContextWrapper(base) {
    companion object{
        const val CHANNEL_MESSAGES_ID = "vn.bn.teams.handnotes.message"
        const val NAME = "Hand Notes"
    }

    var notificationManager: NotificationManager? = null

    init {
        createChanel()
    }

    private fun getBitmapFromVectorDrawable(context: Context?, drawableId: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(
            context!!, drawableId
        )!!
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun createChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_MESSAGES_ID, NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            getManager()?.createNotificationChannel(notificationChannel)
        }
    }

    fun getManager(): NotificationManager? {
        if (notificationManager == null) {
            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        }
        return notificationManager
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun getNotification(
        title: String?,
        body: String?,
        icon: Bitmap?,
        pendingIntent: PendingIntent?,
        soundUri: Uri?,
        context: Context?
    ): Notification.Builder {
        val largeIcon: Bitmap =
            (icon ?: getBitmapFromVectorDrawable(context, R.mipmap.ic_launcher_round)) as Bitmap

        return Notification.Builder(applicationContext, CHANNEL_MESSAGES_ID)
            .setContentIntent(pendingIntent)
            .setLargeIcon(largeIcon)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.notification)
    }
}
