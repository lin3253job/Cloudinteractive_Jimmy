package com.example.cloudinteractive_jimmy.imageCache

import android.graphics.Bitmap

class DoubleCache: ImageCache {
    var memoryCache: ImageCache = MemoryCache()
    var diskCache: ImageCache = DiskCache()

    //確認存取地點
    override fun get(id: String): Bitmap? {
        var bitmap = memoryCache.get(id)
        if (bitmap == null) {
            bitmap = diskCache.get(id)
        }
        return bitmap
    }

    //同時存入file及memoryCache
    override fun put(id: String, bitmap: Bitmap) {
        memoryCache.put(id, bitmap)
        diskCache.put(id, bitmap)
    }
}