package com.example.cloudinteractive_jimmy.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.cloudinteractive_jimmy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null

    private val binding: ActivityMainBinding get()= _binding!!

    private val requestCode = 1

    private val permissionStorage = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
    )


    //詢問使用者是否同意存取本地資料
    private fun verifyStoragePermissions(activity: Activity?) {

        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity,
                permissionStorage,
                requestCode
            )
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        verifyStoragePermissions(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}