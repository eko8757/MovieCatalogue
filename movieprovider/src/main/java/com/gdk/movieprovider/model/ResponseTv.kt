package com.gdk.movieprovider.model

import android.database.Cursor
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gdk.movieprovider.model.StringResponseMovie.Companion.title
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class StringResponseTvShow{
    companion object{
        const val id = "id"
        const val original_name = "original_name"
        const val poster_path = "poster_path"
        const val overview = "overview"
        const val first_air_date = "first_air_date"
        const val vote_average = "vote_average"
    }
}

class ResponseTv {
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
    var results: List<ResultTvShow>? = null

    @Entity(tableName = "db_tv")
    @Parcelize
    data class ResultTvShow(
        @PrimaryKey
        @SerializedName("id")
        @Expose
        var id: Long? = 0,

        @SerializedName("original_name")
        @Expose
        var original_name: String? = null,

        @SerializedName("poster_path")
        @Expose
        var poster_path: String? = null,

        @SerializedName("overview")
        @Expose
        var overview: String? = null,

        @SerializedName("first_air_date")
        @Expose
        var first_air_date: String? = null,

        @SerializedName("vote_average")
        @Expose
        var vote_average: Double? = 0.0

    ) : Parcelable {
        constructor(cursor: Cursor) : this() {
            id = cursor.getLong(cursor.getColumnIndex(StringResponseTvShow.id))
            original_name = cursor.getString(cursor.getColumnIndex(StringResponseTvShow.original_name))
            poster_path = cursor.getString(cursor.getColumnIndex(StringResponseTvShow.poster_path))
            overview = cursor.getString(cursor.getColumnIndex(StringResponseTvShow.overview))
            first_air_date = cursor.getString(cursor.getColumnIndex(StringResponseTvShow.first_air_date))
            vote_average = cursor.getDouble(cursor.getColumnIndex(StringResponseTvShow.vote_average))
        }
    }
}