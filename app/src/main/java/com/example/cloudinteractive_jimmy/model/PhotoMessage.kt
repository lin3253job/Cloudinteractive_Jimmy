package com.example.cloudinteractive_jimmy.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//取得Json回傳物件
@Parcelize
data class PhotoMessage(
    @SerializedName("albumId") val albumId: Int,
    @SerializedName("id")val id: Int,
    @SerializedName("title")val title: String,
    @SerializedName("url") val url: String,
    @SerializedName("thumbnailUrl")val thumbnailUrl: String
) : Parcelable


//取得後轉成dataBaseList
fun List<PhotoMessage>.asLocalModel(): List<DatabasePhoto> {
    return map {
        DatabasePhoto(
            it.id.toString(),
            it.title,
            it.url,
            it.thumbnailUrl
        )
    }
}


