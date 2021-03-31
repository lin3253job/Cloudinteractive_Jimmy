package com.example.cloudinteractive_jimmy.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cloudinteractive_jimmy.model.DatabasePhoto
import com.example.cloudinteractive_jimmy.network.PhotosNetworkService
import com.example.cloudinteractive_jimmy.repository.PhotoDatabase.Companion.getDatabase
import com.example.cloudinteractive_jimmy.sealed.PhotoStatus
import com.example.cloudinteractive_jimmy.repository.RepositoryDataSource
import kotlinx.coroutines.launch

class AllImageViewModel(
    application: Application
) : AndroidViewModel(application) {


    private val photosRepository =
        RepositoryDataSource(
            getDatabase(application)
        )

    private val _dataBasePhoto = MutableLiveData<List<DatabasePhoto>>()
    private val _photoStatusLiveData = MutableLiveData<PhotoStatus>()

    val photoStatusLiveData: LiveData<PhotoStatus>
        get() = _photoStatusLiveData

    val dataBasePhoto: LiveData<List<DatabasePhoto>>
        get() = _dataBasePhoto


    fun checkDatabasePhotoOrRefresh() {

        viewModelScope.launch {

            val photos = photosRepository.getDatabasePhoto()
            Log.e("photos", "$photos?")

            if (photos.isNotEmpty()) {
                Log.e("database", "dataBase有資料")
                _dataBasePhoto.value = photos
                _photoStatusLiveData.value =
                    PhotoStatus.GetDataSuccess
            } else {
                Log.e("database", "dataBase無資料")
                val photoStatus = updatePhotos()
                val dataBasePhotoList = photosRepository.getDatabasePhoto()
                _dataBasePhoto.value = dataBasePhotoList
                _photoStatusLiveData.value = photoStatus
            }

        }
    }



    //執行連線及回傳UI狀態
    private suspend fun updatePhotos(): PhotoStatus {

        PhotosNetworkService.getPhotos().fold(

            onSuccess = {

                photosRepository.updatePhotos(it)
                Log.e("success?", it.toString())

                return PhotoStatus.GetDataSuccess
            },

            onFailure = {
                Log.e("failure?", it.toString())
                return PhotoStatus.LinkFailure
            }
        )


    }


}