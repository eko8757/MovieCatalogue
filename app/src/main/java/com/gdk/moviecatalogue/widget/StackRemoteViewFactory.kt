package com.gdk.moviecatalogue.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.gdk.moviecatalogue.BuildConfig
import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.db.DbHelper
import java.net.URL

class StackRemoteViewFactory(val ctx: Context): RemoteViewsService.RemoteViewsFactory {

    private var images: ArrayList<Bitmap> = ArrayList()

    override fun onCreate() {}

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        images.clear()
        val getMovies = DbHelper.getinstance(ctx).movieDao().getAllDataMovie()
        val getTv = DbHelper.getinstance(ctx).tvDao().getAllDataTv()

        //movie
        for (data in getMovies) {
            val url = URL(BuildConfig.MOVIE_PATH + data.poster_path)
            images.add(BitmapFactory.decodeStream(url.openConnection().getInputStream()))
        }

        //tv show
        for (data in getTv) {
            val url = URL(BuildConfig.MOVIE_PATH + data.poster_path)
            images.add(BitmapFactory.decodeStream(url.openConnection().getInputStream()))
        }
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val view = RemoteViews(ctx.packageName, R.layout.item_widget)
        view.setImageViewBitmap(R.id.img_wgd_favorite, images[position])
        return view
    }

    override fun getCount(): Int = images.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {}
}