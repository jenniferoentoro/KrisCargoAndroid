package com.example.projectpaba

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val channelId = "notification_channel"
const val channelName = "com.example.projectpaba"
class MyFirebaseMessagingService : FirebaseMessagingService() {

    lateinit var db : FirebaseFirestore



    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.getNotification() != null) {
            db = FirebaseFirestore.getInstance()
            val title = remoteMessage.notification!!.title!!
            val notificationId = remoteMessage.messageId
            val notificationTime = remoteMessage.sentTime
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val date = Date(notificationTime)
            val dateString = dateFormat.format(date)
            val message = remoteMessage.notification!!.body!!
            generateNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)

            // Add a new document with a generated ID
            db.collection("notifications").document(notificationId.toString()).set(hashMapOf(
                "title" to title,
                "message" to message,
                "timestamp" to dateString
            ))
                .addOnSuccessListener {
                    Log.d("TAG", "Notification added with ID: $notificationId")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding notification", e)
                }



        }
    }

    fun getRemoteView(title:String, message:String) : RemoteViews {
        val remoteView = RemoteViews(channelName, R.layout.notification)

        remoteView.setTextViewText(R.id.title, title)

        remoteView.setTextViewText(R.id.message, message)
        remoteView.setImageViewResource(R.id.app_logo, R.drawable.kriscargo_logo2)

        return remoteView
    }

    fun generateNotification(title:String, message:String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT)

        // channel id, channel name
        var builder:NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title) // set the title for the notification
            .setContentText(message) // set the message for the notification
            .setSmallIcon(R.drawable.kriscargo_logo2) // set the icon for the notification
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())
    }
}