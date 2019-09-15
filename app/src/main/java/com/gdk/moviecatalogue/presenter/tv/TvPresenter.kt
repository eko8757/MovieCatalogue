package com.gdk.moviecatalogue.presenter.tv

import android.content.Context
import android.content.Intent
import com.gdk.moviecatalogue.BuildConfig
import com.gdk.moviecatalogue.model.ResponseTv
import com.gdk.moviecatalogue.services.BaseApi
import com.gdk.moviecatalogue.ui.tv.DetailTvActivity
import com.gdk.moviecatalogue.view.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class TvPresenter(val view: MainView.TvShowView, var factory: BaseApi) : MainView.TvShowPresenter {
    private var myCompositeDisposable: CompositeDisposable? = null
    private var tvList: ArrayList<ResponseTv.ResultTvShow>? = null

    override fun getTvShow() {
        view.showProgress()
        myCompositeDisposable = CompositeDisposable()
        myCompositeDisposable?.add(
            factory.getDiscoverTv(BuildConfig.MOVIE_API_KEY, "en-US")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<ResponseTv>() {
                    override fun onComplete() {
                        view.hideProgress()
                    }

                    override fun onNext(t: ResponseTv) {
                        view.showData(t.results as ArrayList<ResponseTv.ResultTvShow>)
                        tvList = if (t.results == null) {
                            ArrayList()
                        } else {
                            t.results as ArrayList<ResponseTv.ResultTvShow>
                        }
                        view.hideProgress()
                    }

                    override fun onError(e: Throwable) {
                        view.hideProgress()
                    }
                })
        )
    }

    override fun goToDetailTvShow(context: Context, position: Int) {
        val i = Intent(context, DetailTvActivity::class.java)
        i.putExtra("Data", tvList?.get(position))
        context.startActivity(i)
    }
}