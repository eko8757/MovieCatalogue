package com.gdk.moviecatalogue.model.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gdk.moviecatalogue.model.ResponseTv

@Dao
interface TvDao {

    @Insert
    fun insertTvshow(data : ResponseTv.ResultTvShow)

    @Query("SELECT * FROM tv_show_db")
    fun getAllDataTv() : List<ResponseTv.ResultTvShow>

    @Query("SELECT * FROM tv_show_db WHERE id == :id")
    fun getSelectDataTv(id : Long) : ResponseTv.ResultTvShow

    @Query("DELETE FROM tv_show_db WHERE id == :id")
    fun removeData(id : Long)

    @Query("SELECT * FROM tv_show_db")
    fun getAllTvCursor() : Cursor
}