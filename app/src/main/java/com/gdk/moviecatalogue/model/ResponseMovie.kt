package com.gdk.moviecatalogue.model

import android.content.ContentValues
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


class StringResponseMovie {
    companion object{
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
    var total_results: Long? = 0

    @SerializedName("total_pages")
    @Expose
    var total_pages: Long? = 0

    @SerializedName("results")
    @Expose
    var results: List<ResultMovie>? = null

    @Entity(tableName = "db_movie")
    @Parcelize
    data class ResultMovie (
        @PrimaryKey
        @SerializedName(StringResponseMovie.id)
        @Expose
        var id: Long? = 0,

        @SerializedName(StringResponseMovie.title)
        @Expose
        var title: String? = null,

        @SerializedName(StringResponseMovie.poster_path)
        @Expose
        var poster_path: String? = null,

        @SerializedName(StringResponseMovie.overview)
        @Expose
        var overview: String? = null,

        @SerializedName(StringResponseMovie.release_date)
        @Expose
        var release_date: String? = null,

        @SerializedName(StringResponseMovie.vote_average)
        @Expose
        var vote_average: Double? = 0.0
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