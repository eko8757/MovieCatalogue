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

    private val factory = BaseApi.create()
    private var myCompositeDisposable: CompositeDisposable? = null

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

    override fun setReleaseTodayMovieAlarm(isOn: Boolean, context: Context, data: String) {
        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance()
        val dailyCalendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        if (dailyCalendar.after(calendar)) {
            calendar.add(Calendar.DATE, 1)
        }

        val intentRelease = Intent(context, AlarmReleaseReceiver::class.java)
        intentRelease.putExtra(StaticString.DATA_MOVIE, data)
        val pendingIntent = PendingIntent.getBroadcast(context, UUID.randomUUID().hashCode(), intentRelease, FLAG_UPDATE_CURRENT)
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

    override fun getReleaseToday(context: Context?) {

        view.showProgress()
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()
        myCompositeDisposable = CompositeDisposable()
        myCompositeDisposable?.add(
            factory.releaseToday(BuildConfig.MOVIE_API_KEY, today, today)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<ResponseMovie>() {
                    override fun onComplete() {
                        view.hideProgress()
                    }

                    override fun onNext(t: ResponseMovie) {
                        view.hideProgress()
                        context?.let { it ->
                            if (t.results != null) {
                                val data = t.results as ArrayList<ResponseMovie.ResultMovie>
                                val movieTitle = data.joinToString(limit = 10, separator = ", ") {
                                    it.title.toString()
                                }
                                setReleaseTodayMovieAlarm(true, it, movieTitle)
                                view.hideProgress()
                            } else {
                                setReleaseTodayMovieAlarm(true, it, it.getString(R.string.no_movie_found))
                                view.hideProgress()
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        context?.let {
                            setReleaseTodayMovieAlarm(true, it, it.getString(R.string.no_movie_found))
                        }
                        view.hideProgress()
                    }
                })
        )
    }
}