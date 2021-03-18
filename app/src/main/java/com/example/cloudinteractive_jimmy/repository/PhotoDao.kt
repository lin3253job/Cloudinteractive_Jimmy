package com.example.cloudinteractive_jimmy.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cloudinteractive_jimmy.model.DatabasePhoto

//定義room的fun
@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(photos: List<DatabasePhoto>)

    @Query("SELECT * FROM photo_table ")
    fun getPhotos(): List<DatabasePhoto>

}