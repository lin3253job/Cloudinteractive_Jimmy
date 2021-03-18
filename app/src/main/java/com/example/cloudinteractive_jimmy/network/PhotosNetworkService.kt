package com.example.cloudinteractive_jimmy.network

import com.example.cloudinteractive_jimmy.model.PhotoMessage
import com.example.cloudinteractive_jimmy.network.ApiService
import com.example.cloudinteractive_jimmy.network.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object PhotosNetworkService :
    ApiService {

    //使用retrofit連線取得Json資料
    override suspend fun getPhotos(): Result<List<PhotoMessage>> =
        withContext(Dispatchers.IO) {

            kotlin.runCatching {
                val response = RetrofitManager().photoAPIService.requestPhotoData()

                response.body() ?: error("empty body")
            }

        }

}