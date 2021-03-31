package com.example.cloudinteractive_jimmy.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cloudinteractive_jimmy.SingleLiveEvent
import com.example.cloudinteractive_jimmy.model.DatabasePhoto


class SharedViewModel : ViewModel() {
    //RecyclerView用的資料

    //記綠被點選的Item，在DetailFragment就取selected來使用
    private val _selectedItem: MutableLiveData<DatabasePhoto> = MutableLiveData()

    val selectedItem: LiveData<DatabasePhoto>
        get() = _selectedItem


    var checkNavigate = SingleLiveEvent<Boolean>()


    //點選RecyclerView裡的一個Item
    fun openItem(databasePhoto: DatabasePhoto) {
        checkNavigate.value = true
        _selectedItem.value = databasePhoto
    }
}