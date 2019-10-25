package com.gdk.moviecatalogue.presenter

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.util.Log
import com.gdk.moviecatalogue.BuildConfig
import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.alarm.AlarmDailyReceiver
import com.gdk.moviecatalogue.alarm.AlarmReleaseReceiver
import com.gdk.moviecatalogue.model.ResponseMovie
import com.gdk.moviecatalogue.services.BaseApi
import com.gdk.moviecatalogue.util.StaticString
import com.gdk.moviecatalogue.view.SettingsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class SettingsPresenter(val view: SettingsView.ViewSetting): SettingsView.PresenterSetting {

    override fun setDailyAlarm(isOn: Boolean, context: Context) {
        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance()
        val dailyCalendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, 7)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        if (dailyCalendar.after(calendar)) {
            calendar.add(Calendar.DATE, 1)
        }

        val intentDaily = Intent(context, AlarmDailyReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intentDaily, 0)

        if (isOn) {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        } else {
            alarmManager.cancel(pendingIntent)
        }
    }

    override fun setReleaseTodayMovieAlarm(isNotif: Boolean, isRepeat: Boolean, context: Context) {
        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance()
        val calendarDaily = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        if (calendarDaily.after(calendar)) {
            calendar.add(Calendar.DATE, 1)
        }

        val intent = Intent(context, AlarmReleaseReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        if (isRepeat) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        } else {
            alarmManager.cancel(pendingIntent)
        }
    }
}