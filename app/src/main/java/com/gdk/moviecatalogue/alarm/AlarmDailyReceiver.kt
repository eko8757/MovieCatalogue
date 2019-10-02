package com.gdk.moviecatalogue.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.ui.MainActivity
import com.gdk.moviecatalogue.util.StaticString

class AlarmDailyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context.let {
            val notificationBuilder = it?.let { it1 -> NotificationCompat.Builder(it1, StaticString.CHANNEL_ID) }
            val i = Intent(it, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(it, 0, i, FLAG_UPDATE_CURRENT)

            notificationBuilder?.setAutoCancel(true)
                ?.setContentIntent(pendingIntent)
                ?.setDefaults(Notification.DEFAULT_ALL)
                ?.setWhen(System.currentTimeMillis())
                ?.setSmallIcon(R.drawable.ic_movie_black_24dp)
                ?.setLargeIcon(BitmapFactory.decodeResource(it.resources, R.drawable.ic_movie_black_24dp))
                ?.setContentTitle(it.resources?.getString(R.string.app_name))
                ?.setContentText(it.resources?.getString(R.string.daily_notification))
                ?.setDefaults(Notification.DEFAULT_LIGHTS.or(Notification.DEFAULT_SOUND))
                ?.setContentInfo(it.resources?.getString(R.string.information))

            val notificationManager: NotificationManager = it?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(StaticString.CHANNEL_ID, StaticString.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
                notificationBuilder?.setChannelId(StaticString.CHANNEL_ID)
                notificationManager.createNotificationChannel(notificationChannel)
            }

            val notification = notificationBuilder?.build()
            notificationManager.notify(StaticString.NOTIFICATION_ID, notification)
        }
    }
}