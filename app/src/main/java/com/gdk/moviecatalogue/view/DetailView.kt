package com.gdk.moviecatalogue.view

interface DetailView {
    fun showProgress()
    fun hideProgress()
    fun makeToast(msg: String)
}