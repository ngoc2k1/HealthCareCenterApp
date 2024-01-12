package com.example.myapplication.firebase

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.myapplication.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            configToSendMessageNotification(remoteMessage)
        } else {
            sendNormalMessageNotification(remoteMessage)
        }
    }
    private fun configToSendMessageNotification(remoteMessage: RemoteMessage) {

        val data = remoteMessage.data
        val title = data["title"]
        val content = data["content"]
        val intent = Intent()
        var flag = PendingIntent.FLAG_UPDATE_CURRENT
        flag = flag or PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getActivity(this, title.hashCode(), intent, flag)
        val defSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification1 = MessagesNotification(this)
        val builder: Notification.Builder = notification1.getNotification(
            title, content, null, pendingIntent, defSoundUri,
            applicationContext
        )
        notification1.getManager()!!.notify(title.hashCode(), builder.build())
    }

    private fun sendNormalMessageNotification(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        val title = data["title"]
        val content = data["content"]
        val intent = Intent()

        var flag = PendingIntent.FLAG_UPDATE_CURRENT
        flag = flag or PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getActivity(this,title.hashCode() , intent, flag)
        val defSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this)
            .setContentText(content)
            .setSound(defSoundUri)
            .setContentTitle(title)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.notification)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(title.hashCode(), builder.build())
    }
}