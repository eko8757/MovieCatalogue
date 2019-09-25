package com.gdk.moviecatalogue.model

import android.content.ContentValues
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


class StringResponseMovie {
    companion object {
        const val id = "id"
        const val title = "title"
        const val poster_path = "poster_path"
        const val overview = "overview"
        const val release_date = "release_date"
        const val vote_average = "vote_average"

    }
}


class ResponseMovie {
    @SerializedName("page")
    @Expose
    var page: Int? = 0

    @SerializedName("total_results")
    @Expose
    var total_result: Long? = 0

    @SerializedName("total_page")
    @Expose
    var total_page: Long? = 0

    @SerializedName("results")
    @Expose
    var results: List<ResultMovie>? = null

    @Entity(tableName = "db_movie")
    @Parcelize
    data class ResultMovie(

        @SerializedName("vote_count")
        var vote_count: Long? = 0,

        @PrimaryKey
        @SerializedName("id")
        var id: Long? = 0,

        @SerializedName("video")
        var video: Boolean? = false,

        @SerializedName("vote_average")
        var vote_average: Double? = 0.0,

        @SerializedName("title")
        var title: String? = null,

        @SerializedName("popularity")
        var popularity: Double? = 0.0,

        @SerializedName("poster_path")
        var poster_path: String? = null,

        @SerializedName("original_languange")
        var original_languange: String? = null,

        @SerializedName("original_title")
        var original_title: String? = null,

        @SerializedName("backdrop_path")
        var backdrop_path: String? = null,

        @SerializedName("adult")
        var adult: Boolean? = false,

        @SerializedName("overview")
        var overview: String? = null,

        @SerializedName("release_date")
        var release_date: String? = null
    ) : Parcelable {
        fun values() : ContentValues {
            val contentValues = ContentValues()
            contentValues.put(StringResponseMovie.id, id)
            contentValues.put(StringResponseMovie.title, title)
            contentValues.put(StringResponseMovie.poster_path, poster_path)
            contentValues.put(StringResponseMovie.overview, overview)
            contentValues.put(StringResponseMovie.release_date, release_date)
            contentValues.put(StringResponseMovie.vote_average, vote_average)
            return contentValues
        }
    }
}