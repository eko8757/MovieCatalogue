package com.gdk.moviecatalogue.services

import com.gdk.moviecatalogue.model.ResponseMovie
import com.gdk.moviecatalogue.model.ResponseTv
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface BaseApi {

    @GET("discover/movie?")
    fun getDiscoverMovie(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ) : Observable<ResponseMovie>

    @GET("tv/popular?")
    fun getDiscoverTv(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ) : Observable<ResponseTv>

    @GET("search/movie?")
    fun searchMovie(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): Observable<ResponseMovie>

    @GET("search/tv?")
    fun searchTv(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): Observable<ResponseTv>

    @GET("discover/movie?")
    fun releaseToday(
        @Query("api_key") api_key: String,
        @Query("primary_release_date.lte") gte: String,
        @Query("primary_release_date.gte") lte: String
    ): Observable<Response<ResponseMovie>>

    companion object {
        var URL: String = "https://api.themoviedb.org/3/"
        fun create(): BaseApi {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val clientBuilder = OkHttpClient.Builder()
            clientBuilder.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
            val client = clientBuilder.build()
            val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit.create(BaseApi::class.java)
        }
    }
}