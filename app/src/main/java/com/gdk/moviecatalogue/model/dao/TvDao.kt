package com.gdk.moviecatalogue.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gdk.moviecatalogue.model.ResponseTv

@Dao
interface TvDao {

    @Insert
    fun insertTvshow(data : ResponseTv.ResultTvShow)

    @Query("SELECT * FROM db_tv")
    fun getAllDataTv() : List<ResponseTv.ResultTvShow>

    @Query("SELECT * FROM db_tv WHERE id == :id")
    fun getSelectDataTv(id : Long) : ResponseTv.ResultTvShow

    @Query("DELETE FROM db_tv WHERE id == :id")
    fun removeData(id : Long)
}