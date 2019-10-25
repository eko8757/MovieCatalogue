package com.gdk.moviecatalogue.view

import android.content.Context

interface SettingsView {

    interface ViewSetting {
        fun setDailyAlarm()
        fun setReleaseMovieToday()
        fun setEnglish()
        fun setIndonesian()
        fun showProgress()
        fun hideProgress()
    }

    interface PresenterSetting {
        fun setDailyAlarm(isOn : Boolean, context: Context)
        fun setReleaseTodayMovieAlarm(isNotif: Boolean, isRepeat: Boolean, context: Context)
    }
}