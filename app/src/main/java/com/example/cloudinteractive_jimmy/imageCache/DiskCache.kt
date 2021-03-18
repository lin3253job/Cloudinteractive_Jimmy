package com.example.cloudinteractive_jimmy.imageCache

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream


class DiskCache : ImageCache {


    //本地資料夾路徑
    private val cachePath =
        Environment.getExternalStorageDirectory().absolutePath + "/PhotoTempCache"

    /**
     * 從本地讀取圖片
     * @param id
     */
    private fun getBitmapFromLocal(id: String?): Bitmap? {
        return try {
            val file = File(cachePath, id)
            BitmapFactory.decodeStream(FileInputStream(file))
        } catch (e: Exception) {
            null
        }
    }


    override fun get(id: String): Bitmap? {
        return getBitmapFromLocal(id)


    }


    override fun put(id: String, bitmap: Bitmap) {

        //使用MD5編碼取得唯一識別碼
        val md5: String? = MD5Encoder.encode(id)


        val baseFile = File(cachePath)

        //如果資料夾不存在或遺失，則創建新資料夾
        if (!baseFile.exists() || !baseFile.isDirectory) {
            baseFile.mkdirs()
        }

        //創建bitmap資料夾
        val bitmapFile = File(baseFile, md5)

        try {
            //將bitmap放入資料夾
            val fos = FileOutputStream(bitmapFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }
}