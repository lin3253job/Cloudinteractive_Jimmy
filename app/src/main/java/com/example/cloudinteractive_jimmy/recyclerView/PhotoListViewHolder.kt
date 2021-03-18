package com.example.cloudinteractive_jimmy.recyclerView

import android.view.View
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.enzo.livedata_mvvm.imageCache.DoubleCache
import com.example.cloudinteractive_jimmy.imageCache.ImageLoader
import com.example.cloudinteractive_jimmy.imageCache.MD5Encoder
import com.example.cloudinteractive_jimmy.R
import com.example.cloudinteractive_jimmy.model.DatabasePhoto
import com.example.cloudinteractive_jimmy.viewModel.AllImageViewModel
import kotlinx.android.synthetic.main.item_image.view.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class PhotoListViewHolder(v: View) : RecyclerView.ViewHolder(v) {


    private val idTextView = itemView.id_textView

    private val titleTextView = itemView.title_textView

    private val photoImageView = itemView.photo_imageView

    //使用job解決復用
    private var job: Job? = null


    fun onBind(photo: DatabasePhoto, allImageViewModel: AllImageViewModel) {

        idTextView.text = photo.id

        titleTextView.text = photo.title

        photoImageView.setImageResource(R.drawable.ic_launcher_foreground)


        //給image放上tag
        photoImageView.tag = MD5Encoder.encode(photo.url)

        job?.cancel()

        job = allImageViewModel.viewModelScope.launch {
            ImageLoader.displayImage(
                photo.url,
                photoImageView,
                DoubleCache()
            )
        }

    }


}