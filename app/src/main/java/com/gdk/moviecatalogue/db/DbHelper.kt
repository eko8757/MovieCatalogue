package com.gdk.moviecatalogue.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gdk.moviecatalogue.model.ResponseMovie
import com.gdk.moviecatalogue.model.ResponseTv
import com.gdk.moviecatalogue.model.dao.MovieDao
import com.gdk.moviecatalogue.model.dao.TvDao

@Database(entities = [(ResponseMovie.ResultMovie::class),(ResponseTv.ResultTvShow::class)], version = 5, exportSchema = false)
abstract class DbHelper : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvDao

    companion object {
        private var instance: DbHelper? = null

        @Synchronized
        fun getinstance(ctx: Context): DbHelper {
            if (instance == null) {
                instance = Room.databaseBuilder(ctx.applicationContext, DbHelper::class.java, "favorite")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}