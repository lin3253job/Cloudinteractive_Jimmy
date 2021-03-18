package com.example.cloudinteractive_jimmy.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.cloudinteractive_jimmy.*
import com.example.cloudinteractive_jimmy.model.DatabasePhoto
import com.example.cloudinteractive_jimmy.viewModel.AllImageViewModel
import kotlinx.coroutines.Job

class PhotoListAdapter(
    private val onItemClick:(DatabasePhoto) -> Unit, private val allImageViewModel: AllImageViewModel
) :
    ListAdapter<DatabasePhoto, PhotoListViewHolder>(
        PhotoDiffItemCallBack()
    ) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return PhotoListViewHolder(v)
    }


    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {


        val databasePhoto = getItem(position)
        holder.onBind(databasePhoto,allImageViewModel)


        holder.itemView.setOnClickListener {
            onItemClick(databasePhoto)
        }

    }


}
