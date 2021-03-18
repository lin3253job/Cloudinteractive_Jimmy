package com.example.cloudinteractive_jimmy.sealed


//定義畫面顯示狀態
sealed class PhotoStatus {

    object LinkFailure : PhotoStatus()

    object GetDataSuccess : PhotoStatus()

}