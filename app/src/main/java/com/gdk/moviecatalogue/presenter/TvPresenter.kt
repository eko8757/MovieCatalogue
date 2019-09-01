package com.gdk.moviecatalogue.presenter

import com.gdk.moviecatalogue.BuildConfig
import com.gdk.moviecatalogue.model.TvResponse
import com.gdk.moviecatalogue.services.BaseApi
import com.gdk.moviecatalogue.view.TvView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class TvPresenter(val view: TvView, var factory: BaseApi) {
    private var myCompositeDisposable: CompositeDisposable? = null
    private var tvList = ArrayList<TvResponse.ResultTvShow>()

    fun getDataTv() {
        view.showProgress()
        myCompositeDisposable = CompositeDisposable()
        myCompositeDisposable?.add(
            factory.getDiscoverTv(BuildConfig.MOVIE_API_KEY, "en-US")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<TvResponse>() {
                    override fun onComplete() {
                        view.hideProgress()
                    }

                    override fun onNext(t: TvResponse) {
                        view.showData(t.results as ArrayList<TvResponse.ResultTvShow>)
                        tvList = if (t.results == null) {
                            ArrayList()
                        } else {
                            t.results as ArrayList<TvResponse.ResultTvShow>
                        }
                        view.hideProgress()
                    }

                    override fun onError(e: Throwable) {
                        view.makeToast(e.message.toString())
                        view.hideProgress()
                    }
                })
        )
    }
}