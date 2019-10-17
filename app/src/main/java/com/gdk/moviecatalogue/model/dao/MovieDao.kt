package com.gdk.moviecatalogue.model.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gdk.moviecatalogue.model.ResponseMovie

@Dao
interface MovieDao {
    @Insert
    fun insertMovie(data: ResponseMovie.ResultMovie)

    @Query("SELECT * FROM movie_db")
    fun getAllDataMovie() : List<ResponseMovie.ResultMovie>

    @Query("SELECT * FROM movie_db WHERE id == :id")
    fun getSelectDataMovie(id: Long) : ResponseMovie.ResultMovie

    @Query("DELETE FROM movie_db WHERE id == :id")
    fun removeData(id: Long)

    @Query("SELECT * FROM movie_db")
    fun getAllMovieCursor() : Cursor
}