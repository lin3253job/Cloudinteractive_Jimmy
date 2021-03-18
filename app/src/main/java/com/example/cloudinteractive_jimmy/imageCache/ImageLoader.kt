package com.example.cloudinteractive_jimmy.imageCache

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.example.cloudinteractive_jimmy.R
import kotlinx.android.synthetic.main.all_image_fragment.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference
import java.util.*


object ImageLoader {
    //使用弱參照以防oom
    private var imageViewWeakReference: WeakReference<ImageView>? = null

    private var mImageCache: ImageCache? = null

    private val okHttpClient = OkHttpClient()


    //解析url編碼，若本地有bitmap資料則從本地拿，沒有的話從網路下載顯示
    suspend fun displayImage(url: String, imageView: ImageView, imageCache: ImageCache?) {
        imageViewWeakReference = WeakReference(imageView)

        mImageCache = imageCache
        var md5 = ""

        try {
            md5 = MD5Encoder.encode(url)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        val bitmap = mImageCache?.get(md5)

        if (bitmap != null) {
            if (md5 == imageView.tag) {
                imageView.alpha = 0f
                imageViewWeakReference!!.get()?.setImageBitmap(bitmap)
                imageView.animate().alpha(1f).duration = 500.toLong()
            }
            return
        }
        imageViewWeakReference!!.get()?.let { submitLoadRequest(url, it) }
    }


    //從網路下載圖片並顯示
    private suspend fun submitLoadRequest(url: String, imageView: ImageView) {
        withContext(Dispatchers.IO) {
            var bitmap: Bitmap? = null
            try {
                bitmap = downloadImage(url)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (bitmap == null) {
                return@withContext
            }
            try {
                val md5 = MD5Encoder.encode(url)
                if (md5 == imageView.tag) {
                    withContext(Dispatchers.Main) {
                        imageView.alpha = 0f
                        imageView.setImageBitmap(bitmap)
                        imageView.animate().alpha(1f).duration = 500.toLong()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                mImageCache?.put(url, bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    //使用okHttp進行bitmap網路下載
    @Throws(IOException::class)
    private fun downloadImage(url: String): Bitmap? {
        val bitmap: Bitmap?
        val inStream: InputStream?
        val bis: BufferedInputStream?
        val req = Request.Builder().url(url).header("user-agent", "Chrome 74 on Windows 10").build()
        val response = okHttpClient.newCall(req).execute()
        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        }

        bitmap = if (response.body!!.contentType().toString().toLowerCase(Locale.ROOT)
                .contains("application/json") || response.body!!
                .contentType().toString().toLowerCase(Locale.ROOT).contains("text/plain")
        ) {
            throw IOException("${R.string.connectFail}=$url")
        } else {
            inStream = response.body!!.byteStream()
            bis = BufferedInputStream(inStream)
            BitmapFactory.decodeStream(bis)
        }

        bis.close()
        inStream.close()
        response.body!!.close()

        return bitmap

    }


}