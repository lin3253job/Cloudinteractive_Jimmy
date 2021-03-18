package com.example.cloudinteractive_jimmy.repository

import android.util.Log
import com.example.cloudinteractive_jimmy.network.PhotosNetworkService
import com.example.cloudinteractive_jimmy.model.DatabasePhoto
import com.example.cloudinteractive_jimmy.model.PhotoMessage
import com.example.cloudinteractive_jimmy.model.asLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryDataSource(private val database: PhotoDatabase) :
    Repository {

    //取得room資料
    override suspend fun getDatabasePhoto(): List<DatabasePhoto> = withContext(Dispatchers.IO) {

        return@withContext database.photoDao.getPhotos()

    }


    //載入room資料
    override suspend fun updatePhotos(photoMessageList: List<PhotoMessage>) =
        withContext(Dispatchers.IO) {

            database.photoDao.insertAll(photoMessageList.asLocalModel())

        }


}