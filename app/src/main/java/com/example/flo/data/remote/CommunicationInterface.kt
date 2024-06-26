package com.example.flo.data.remote

import com.example.flo.data.entities.Album

interface CommunicationInterface {
    fun sendData(album: Album)
}