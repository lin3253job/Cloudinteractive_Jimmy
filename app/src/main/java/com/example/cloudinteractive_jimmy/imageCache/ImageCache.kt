package com.example.cloudinteractive_jimmy.imageCache

import android.graphics.Bitmap

interface ImageCache {
    fun get(id: String): Bitmap?
    fun put(id: String, bitmap: Bitmap)
}