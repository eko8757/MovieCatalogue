package com.gdk.moviecatalogue.view

import android.content.Context

interface SettingsView {

    interface ViewSetting {
        fun setDailyAlarm()
        fun setReleaseMovieToday()
        fun showProgress()
        fun hideProgress()
    }

    interface PresenterSetting {
        fun setDailyAlarm(isOn : Boolean, context: Context)
        fun setReleaseTodayMovieAlarm(isOn : Boolean, context: Context, data: String)
        fun getReleaseToday(context: Context?)
    }
}