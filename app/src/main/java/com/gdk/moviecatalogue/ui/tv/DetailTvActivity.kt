package com.gdk.moviecatalogue.ui.tv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gdk.moviecatalogue.BuildConfig
import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.model.TvResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_tv.*

class DetailTvActivity : AppCompatActivity() {

    var items: TvResponse.ResultTvShow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv)

        val i = intent
        items = i.getParcelableExtra("name")
        tv_title_detail.text = items?.title
        tv_release_date.text = items?.first_air_date
        tv_desc_detail.text = items?.overview
        tv_vote_average.text = items?.vote_average.toString()

        val API_URL = BuildConfig.MOVIE_PATH
        Picasso.get().load(API_URL + items?.poster_path).into(img_detail)
    }
}
