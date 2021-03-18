package com.example.cloudinteractive_jimmy.repository

import com.example.cloudinteractive_jimmy.model.DatabasePhoto
import com.example.cloudinteractive_jimmy.model.PhotoMessage

interface Repository {
    suspend fun getDatabasePhoto(): List<DatabasePhoto>
    suspend fun updatePhotos(photoMessageList: List<PhotoMessage>)
}