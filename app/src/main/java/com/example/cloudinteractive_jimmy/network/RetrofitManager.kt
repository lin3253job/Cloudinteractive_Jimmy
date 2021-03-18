package com.example.cloudinteractive_jimmy.network

import com.example.cloudinteractive_jimmy.factories.NullOnEmptyConverterFactory
import com.example.cloudinteractive_jimmy.repository.PhotoAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val BASE_URL = "https://jsonplaceholder.typicode.com/"


class RetrofitManager {


    private val client = OkHttpClient().newBuilder()

        //連線超時，單位設置是60/秒
        .connectTimeout(60, TimeUnit.SECONDS)

        //建立攔截器鏈
        .addInterceptor(Interceptor { chain ->

            //建立請求者
            val builder = chain.request().newBuilder()

            return@Interceptor chain.proceed(builder.build())
        })
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        //處理回傳為空的情況
        .addConverterFactory(NullOnEmptyConverterFactory())
        //解析回傳
        .addConverterFactory(GsonConverterFactory.create())
        //放入okHttp連線基底
        .client(client)
        .build()


    val photoAPIService: PhotoAPI = retrofit.create(
        PhotoAPI::class.java)


}