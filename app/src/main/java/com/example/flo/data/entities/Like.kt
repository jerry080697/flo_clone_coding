package com.example.flo.data.entities

import androidx.room.*

@Entity(tableName = "LikeTable")
data class Like(
    var userId:Int,
    var albumId:Int
){
    @PrimaryKey(autoGenerate = true) var id:Int=0
}
