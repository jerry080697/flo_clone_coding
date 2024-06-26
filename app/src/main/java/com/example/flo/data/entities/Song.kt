package com.example.flo.data.entities

import androidx.room.*

@Entity(tableName = "SongTable")
data class Song(
    val title:String="",
    val singer:String="",
    var second: Int =0,
    var playTime:Int =0,
    var isPlaying: Boolean = false,
    var music: String="",
    var coverImg : Int? = null,
    var isLike:Boolean=false,
    val albumId:Int=0
){
    @PrimaryKey(autoGenerate = true) var id :Int =0
}