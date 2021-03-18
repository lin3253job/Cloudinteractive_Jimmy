package com.example.cloudinteractive_jimmy.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


//儲存在room的物件
@Parcelize
@Entity(tableName = "photo_table")
data class DatabasePhoto(
    @PrimaryKey val id: String,
    val title: String,
    val url: String,
    val thumbnailUrl: String): Parcelable

