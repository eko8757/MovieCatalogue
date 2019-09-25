package com.gdk.movieprovider.view

import android.content.Context
import com.gdk.movieprovider.model.ResponseMovie
import com.gdk.movieprovider.model.ResponseTv

class DetailView {
    interface ViewMovie {
        fun getData()
        fun showData(
            image: String,
            title: String,
            releaseDate: String,
            rating: String,
            description: String
        )
    }

    interface PresenterMovie {
        fun extractData(
            context: Context,
            data: ResponseMovie.ResultMovie
        )
    }

    interface ViewTV {
        fun getData()
        fun showData(
            image: String,
            title: String,
            firstAir: String,
            rating: String,
            description: String
        )
    }

    interface PresenterTV {
        fun extractData(
            context: Context,
            data: ResponseTv.ResultTvShow
        )
    }

}