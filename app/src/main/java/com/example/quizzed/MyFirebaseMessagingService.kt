package com.example.quizzed

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)


        val title = message.notification?.title ?: "નવી ક્વિઝ!"
        val body = message.notification?.body ?: "આજે કંઈક નવું શીખો અને સ્કોર વધારો."

        showNotification(title, body)
    }

    private fun showNotification(title: String, body: String) {
        val channelId = "quiz_notifications"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Quiz Updates", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(android.R.drawable.ic_dialog_info) 
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}

