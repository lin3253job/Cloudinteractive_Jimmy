package com.example.cloudinteractive_jimmy

import android.app.Application
import kotlin.properties.Delegates


//自定義Application，方便需要Context元件調用

class MyApplication : Application() {
    companion object {
        private const val TAG = "MyApplication"
        lateinit var instance: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}
