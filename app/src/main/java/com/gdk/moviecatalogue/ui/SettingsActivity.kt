package com.gdk.moviecatalogue.ui

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.presenter.SettingsPresenter
import com.gdk.moviecatalogue.util.StaticString
import com.gdk.moviecatalogue.view.SettingsView
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.toast
import java.util.*

class SettingsActivity : AppCompatActivity(), SettingsView.ViewSetting, View.OnClickListener {

    lateinit var locale: Locale
    lateinit var config: Configuration
    private lateinit var mPresenter: SettingsPresenter
    private lateinit var progressDialog: ProgressDialog
    private var isDailyAlarm = false
    private var isReleaseAlarm = false
    private var isEnglish = false
    private var isIndonesian = false

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        mPresenter = SettingsPresenter(this)
        isDailyAlarm = Prefs.getBoolean(StaticString.DAILY_ALARM_ON, false)
        isReleaseAlarm = Prefs.getBoolean(StaticString.TODAY_MOVIE_ALARM_ON, false)
        isEnglish = Prefs.getBoolean(StaticString.ENGLISH_LANGUAGE, false)
        isIndonesian = Prefs.getBoolean(StaticString.INDONESIAN_LANGUAGE, false)

        sw_daily_reminder.isChecked = isDailyAlarm
        sw_release_reminder.isChecked = isReleaseAlarm
        sw_english.isChecked = isEnglish
        sw_indonesian.isChecked = isIndonesian

        sw_daily_reminder.setOnClickListener(this)
        sw_release_reminder.setOnClickListener(this)
        sw_english.setOnClickListener(this)
        sw_indonesian.setOnClickListener(this)
        config = baseContext.resources.configuration
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.sw_daily_reminder -> setDailyAlarm()
            R.id.sw_release_reminder -> setReleaseMovieToday()
            R.id.sw_english -> setEnglish()
            R.id.sw_indonesian -> setIndonesian()
        }
    }

    override fun setEnglish() {
        sw_indonesian.isChecked = false
        Prefs.remove(StaticString.INDONESIAN_LANGUAGE)
        Prefs.putBoolean(StaticString.ENGLISH_LANGUAGE, true)
        locale = Locale("en")
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        toast("English")
    }

    override fun setIndonesian() {
        sw_english.isChecked = false
        Prefs.remove(StaticString.ENGLISH_LANGUAGE)
        Prefs.putBoolean(StaticString.INDONESIAN_LANGUAGE, true)
        locale = Locale("in")
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        toast("Indonesian")
    }

    override fun setDailyAlarm() {
        isDailyAlarm = !isDailyAlarm
        applicationContext?.let { mPresenter.setDailyAlarm(isDailyAlarm, it) }
        Prefs.putBoolean(StaticString.DAILY_ALARM_ON, isDailyAlarm)
        sw_daily_reminder.isChecked = isDailyAlarm
    }

    override fun setReleaseMovieToday() {
        isReleaseAlarm = !isReleaseAlarm

        if (isReleaseAlarm) {
            applicationContext?.let { mPresenter.getReleaseToday(applicationContext) }
            hideProgress()
        } else {
            applicationContext?.let { mPresenter.setReleaseTodayMovieAlarm(isReleaseAlarm, it, "") }
            hideProgress()
        }

        Prefs.putBoolean(StaticString.TODAY_MOVIE_ALARM_ON, isReleaseAlarm)
        sw_release_reminder.isChecked = isReleaseAlarm
    }

    override fun showProgress() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage(getString(R.string.loading_message))
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
    }

    override fun hideProgress() {
        progressDialog.dismiss()
    }
}
