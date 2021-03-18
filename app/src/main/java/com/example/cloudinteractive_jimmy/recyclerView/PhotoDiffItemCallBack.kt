package com.example.cloudinteractive_jimmy.recyclerView


import androidx.recyclerview.widget.DiffUtil
import com.example.cloudinteractive_jimmy.model.DatabasePhoto

class PhotoDiffItemCallBack : DiffUtil.ItemCallback<DatabasePhoto>() {


    override fun areItemsTheSame(oldItem: DatabasePhoto, newItem: DatabasePhoto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: DatabasePhoto,
        newItem: DatabasePhoto
    ): Boolean {
        return oldItem == newItem
    }
}