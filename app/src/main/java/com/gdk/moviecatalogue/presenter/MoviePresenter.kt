package com.gdk.moviecatalogue.presenter

import com.gdk.moviecatalogue.BuildConfig
import com.gdk.moviecatalogue.model.MovieResponse
import com.gdk.moviecatalogue.services.BaseApi
import com.gdk.moviecatalogue.view.MovieView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MoviePresenter(val view: MovieView, var factory: BaseApi) {
    private var myCompositeDisposable: CompositeDisposable? = null
    private var movieList = ArrayList<MovieResponse.ResultMovie>()

    fun getDataMovie() {
        view.showProgress()
        myCompositeDisposable = CompositeDisposable()
        myCompositeDisposable?.add(
            factory.getDiscoverMovie(BuildConfig.MOVIE_API_KEY, "en-US")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<MovieResponse>() {
                    override fun onComplete() {
                        view.hideProgress()
                    }

                    override fun onNext(t: MovieResponse) {
                        view.showData(t.results as ArrayList<MovieResponse.ResultMovie>)
                        movieList = if (t.results == null) {
                            ArrayList()
                        } else {
                            t.results as ArrayList<MovieResponse.ResultMovie>
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