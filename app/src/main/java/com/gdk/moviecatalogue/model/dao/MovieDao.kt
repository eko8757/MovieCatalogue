package com.gdk.moviecatalogue.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gdk.moviecatalogue.model.ResponseMovie

@Dao
interface MovieDao {

    @Insert
    fun insertMovie(data: ResponseMovie.ResultMovie)

    @Query("SELECT * FROM db_movie")
    fun getAllDataMovie() : List<ResponseMovie.ResultMovie>

    @Query("SELECT * FROM db_movie WHERE id == :id")
    fun getSelectDataMovie(id: Long) : ResponseMovie.ResultMovie

    @Query("DELETE FROM db_movie WHERE id == :id")
    fun removeData(id: Long)
}