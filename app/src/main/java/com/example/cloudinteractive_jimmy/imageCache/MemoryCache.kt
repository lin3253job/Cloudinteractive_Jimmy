package com.example.cloudinteractive_jimmy.imageCache

import android.graphics.Bitmap
import android.util.LruCache


class MemoryCache//初始化Lru快取
    : ImageCache {


    private var memoryCache: LruCache<String, Bitmap>? = null

    init {
        initImageCache()
    }

    private fun initImageCache() {
        //計算可使用的最大記憶體
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        //取四分之一可使用的記憶體作為快取
        val cacheSize = maxMemory / 4
        memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String?, bitmap: Bitmap): Int {
                return bitmap.rowBytes * bitmap.height / 1024
            }
        }
    }


    override fun get(id: String): Bitmap? {
      return memoryCache?.get(id)
    }

    override fun put(id: String, bitmap: Bitmap) {
        memoryCache?.put(id, bitmap)
    }

}