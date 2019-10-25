package com.gdk.moviecatalogue.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MainReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val i = Intent(context, AlarmServices::class.java)
        context?.startService(i)
    }
}