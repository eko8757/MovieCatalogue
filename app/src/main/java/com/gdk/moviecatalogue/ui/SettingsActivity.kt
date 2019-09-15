package com.gdk.moviecatalogue.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import com.gdk.moviecatalogue.R
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*

class SettingsActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    lateinit var locale: Locale
    lateinit var config: Configuration

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        config = baseContext.resources.configuration
        sw_indonesian.setOnCheckedChangeListener(this)
        sw_english.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when(buttonView?.id) {
            R.id.sw_indonesian -> {
                if (isChecked) {
                    sw_english.isChecked = false
                    locale = Locale("in")
                    config.locale = locale
                    baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
                }
            }

            R.id.sw_english -> {
                if (isChecked) {
                    sw_indonesian.isChecked = false
                    locale = Locale("en")
                    config.locale = locale
                    baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
                }
            }
        }
    }
}
