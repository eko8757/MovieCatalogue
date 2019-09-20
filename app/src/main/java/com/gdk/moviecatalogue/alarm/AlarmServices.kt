package com.gdk.moviecatalogue.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.gdk.moviecatalogue.util.StaticString
import com.pixplicity.easyprefs.library.Prefs
import java.util.*

class AlarmServices: Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()

        val dailyAlarmOn: Boolean = Prefs.getBoolean(StaticString.DAILY_ALARM_ON, false)
        settingsDailyAlarm(dailyAlarmOn)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    private fun settingsDailyAlarm(dailyAlarmOn: Boolean) {
        val mAlarm: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance()
        val mDaily = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 6)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        if (mDaily.after(calendar)) {
            calendar.add(Calendar.DATE, 1)
        }

        val intent = Intent(this, AlarmDailyReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        if (dailyAlarmOn) {
            mAlarm.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        } else {
            mAlarm.cancel(pendingIntent)
        }
    }
}