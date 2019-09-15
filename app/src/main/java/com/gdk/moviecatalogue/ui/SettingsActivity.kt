package com.gdk.moviecatalogue.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.widget.SwitchCompat
import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.util.StaticString
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        sw_indonesian.setOnCheckedChangeListener(this)
        sw_english.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when(buttonView?.id) {
            R.id.sw_indonesian -> {
                if (isChecked) {
                    sw_english.isChecked = false
//                    Prefs.putString(StaticString().LENGUAGE, "in")
                }
            }

            R.id.sw_english -> {
                if (isChecked) {
                    sw_indonesian.isChecked = false
                }
            }
        }
    }
}
