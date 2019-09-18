package com.gdk.moviecatalogue.presenter.movie

import android.content.Context
import android.content.Intent
import com.gdk.moviecatalogue.BuildConfig
import com.gdk.moviecatalogue.model.ResponseMovie
import com.gdk.moviecatalogue.services.BaseApi
import com.gdk.moviecatalogue.ui.movie.DetailMovieActivity
import com.gdk.moviecatalogue.view.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MoviePresenter(val view: MainView.MovieView, var factory: BaseApi) : MainView.MoviePresenter {
    private var myCompositeDisposable: CompositeDisposable? = null
    private var movieList: ArrayList<ResponseMovie.ResultMovie>? = null

    override fun getMovie() {
        view.showProgress()
        myCompositeDisposable = CompositeDisposable()
        myCompositeDisposable?.add(
            factory.getDiscoverMovie(BuildConfig.MOVIE_API_KEY, "en-US")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<ResponseMovie>() {
                    override fun onComplete() {
                        view.hideProgress()
                    }

                    override fun onNext(t: ResponseMovie) {
                        view.showData(t.results as ArrayList<ResponseMovie.ResultMovie>)
                        movieList = if (t.results == null) {
                            ArrayList()
                        } else {
                            t.results as ArrayList<ResponseMovie.ResultMovie>
                        }
                        view.hideProgress()
                    }

                    override fun onError(e: Throwable) {
                        view.hideProgress()
                    }
                })
        )
    }

    override fun goToDetailMovie(context: Context, position: Int) {
        val i = Intent(context, DetailMovieActivity::class.java)
        i.putExtra("Data", movieList?.get(position))
        context.startActivity(i)
    }

    override fun getData(query: String) {
        view.showProgress()
        myCompositeDisposable = CompositeDisposable()
        myCompositeDisposable?.add(
            factory.searchMovie(BuildConfig.MOVIE_API_KEY, "en-US", query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<ResponseMovie>() {
                    override fun onComplete() {
                        view.hideProgress()
                    }

                    override fun onNext(t: ResponseMovie) {
                        view.showData(t.results as ArrayList<ResponseMovie.ResultMovie>)
                        movieList = if (t.results == null) {
                            ArrayList()
                        } else {
                            t.results as ArrayList<ResponseMovie.ResultMovie>
                        }
                        view.hideProgress()
                    }

                    override fun onError(e: Throwable) {
                        view.hideProgress()
                    }
                })
        )
    }
}