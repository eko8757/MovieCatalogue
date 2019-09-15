package com.gdk.moviecatalogue.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

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

        @SerializedName("original_name")
        var original_name: String? = null,

        @SerializedName("name")
        var title: String? = null,

        @SerializedName("popularity")
        var popularity: Double? = 0.0,

        @SerializedName("vote_count")
        var vote_count: Long? = 0,

        @SerializedName("first_air_date")
        var first_air_date: String? = null,

        @SerializedName("backdrop_path")
        var backdrop_path: String? = null,

        @SerializedName("original_languange")
        var original_languange: String? = null,

        @PrimaryKey
        @SerializedName("id")
        var id: Long? = 0,

        @SerializedName("vote_average")
        var vote_average: Double? = 0.0,

        @SerializedName("overview")
        var overview: String? = null,

        @SerializedName("poster_path")
        var poster_path: String? = null
    ) : Parcelable
}