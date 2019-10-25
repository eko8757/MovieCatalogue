package com.gdk.moviecatalogue.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.gdk.moviecatalogue.BuildConfig
import com.gdk.moviecatalogue.model.ResponseMovie
import com.gdk.moviecatalogue.services.BaseApi
import com.gdk.moviecatalogue.ui.MainActivity
import com.gdk.moviecatalogue.util.Commons
import com.gdk.moviecatalogue.util.StaticString
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.io.IOException
import java.net.MalformedURLException

class AlarmReleaseReceiver : BroadcastReceiver() {

    private val factory = BaseApi.create()
    private var myCompositeDisposable: CompositeDisposable? = null
    private var data: String? = null
    private var mBuilder: NotificationCompat.Builder? = null
    private var context: Context? = null

    override fun onReceive(context: Context?, intent: Intent?) {

        mBuilder = NotificationCompat.Builder(context)
        this.context = context

        getDataReleaseToday(
            BuildConfig.MOVIE_API_KEY,
            Commons().getDateToday(),
            Commons().getDateToday()
        )
    }

    fun showNotif(builder: NotificationCompat.Builder, context: Context?, data: String?, requestCode:Int) {

        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(StaticString.TODAY_MOVIE_ALARM_ON, true)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, FLAG_ONE_SHOT)
        var contentAlarm: String? = ""

        if (data == null) {
            contentAlarm =
                context?.resources?.getString(com.gdk.moviecatalogue.R.string.no_movie_found)
                    .toString()
        } else {
            contentAlarm = data
        }
        builder.setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(com.gdk.moviecatalogue.R.drawable.ic_movie_black_24dp)
            .setContentTitle(contentAlarm)
            .setContentText(contentAlarm)
            .setGroup(contentAlarm)
            .setDefaults(Notification.DEFAULT_LIGHTS.or(Notification.DEFAULT_SOUND))
            .setContentInfo(context?.resources?.getString(com.gdk.moviecatalogue.R.string.information))


        val notificationManager: NotificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                StaticString.CHANNEL_ID,
                StaticString.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            builder.setChannelId(StaticString.CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManager.notify(requestCode, notification)

    }

    fun getDataReleaseToday(movieApiKey: String, dateToday: String, dateToday1: String) {
        myCompositeDisposable = CompositeDisposable()
        myCompositeDisposable?.add(factory.releaseToday(
            movieApiKey, dateToday, dateToday1
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : DisposableObserver<Response<ResponseMovie>>() {
                override fun onComplete() {

                }

                override fun onNext(t: Response<ResponseMovie>) {
                    if (t.code() == 200) {
                        var dataArray: ArrayList<String> = ArrayList()
                        for (i in 0 until t.body()?.results!!.size) {
                            data = t.body()?.results?.get(i)?.title.toString()
                            dataArray.add(t.body()?.results?.get(i)?.title.toString())

                        }
                        showAlarm(t.body()?.results?.size, dataArray)

                    } else {
                        mBuilder?.let { showNotif(it, context, null, 0) }
                    }
                }

                override fun onError(e: Throwable) {

                }
            })
        )
    }

    fun showAlarm(size: Int?, dataArray: ArrayList<String>) {
        Thread(Runnable {
            for (i in 0 until size!!) {
                try {
                    mBuilder?.let { showNotif(it, context, dataArray.get(i), i) }
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }).start()
    }
}