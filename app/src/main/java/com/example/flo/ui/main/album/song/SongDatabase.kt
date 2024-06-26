package com.example.flo.ui.main.album.song

import android.content.Context
import androidx.room.*
import com.example.flo.data.entities.Album
import com.example.flo.data.entities.Like
import com.example.flo.data.entities.Song
import com.example.flo.data.entities.User
import com.example.flo.utils.AlbumDao
import com.example.flo.utils.SongDao
import com.example.flo.utils.UserDao

@Database(entities = [Song::class, Album::class, User::class, Like::class], version = 1)
abstract class SongDatabase:RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun albumDao(): AlbumDao
    abstract fun userDao(): UserDao

    companion object{
        private var instance: SongDatabase?=null

        @Synchronized
        fun getInstance(context: Context): SongDatabase?{
            if(instance ==null){
                synchronized(SongDatabase::class){
                    instance =Room.databaseBuilder(
                        context.applicationContext,
                        SongDatabase::class.java,
                        "song-database"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}