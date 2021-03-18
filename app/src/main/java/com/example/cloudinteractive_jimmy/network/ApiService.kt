package com.example.cloudinteractive_jimmy.network

import com.example.cloudinteractive_jimmy.model.PhotoMessage


interface ApiService {
    suspend fun getPhotos(): Result<List<PhotoMessage>>
}