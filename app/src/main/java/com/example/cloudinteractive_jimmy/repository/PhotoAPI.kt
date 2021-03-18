package com.example.cloudinteractive_jimmy.repository

import com.example.cloudinteractive_jimmy.model.PhotoMessage
import retrofit2.Response
import retrofit2.http.GET

interface PhotoAPI {
    @GET("photos")
    suspend fun requestPhotoData(): Response<List<PhotoMessage>>
}