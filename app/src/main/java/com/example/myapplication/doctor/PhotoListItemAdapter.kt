package com.example.myapplication.doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemPhotoBinding
import com.example.myapplication.model.PhotoUI

class PhotoListItemAdapter(
) : ListAdapter<PhotoUI, PhotoListItemAdapter.PhotoVH>(
    ExampleListDiffUtil()
) {

    inner class PhotoVH(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: PhotoUI) {
            binding.apply {
                Glide.with(imageView).load(data.photo.toUri()).centerCrop()
                    .into(imageView)
            }
        }
    }

    class ExampleListDiffUtil : DiffUtil.ItemCallback<PhotoUI>() {
        override fun areContentsTheSame(
            oldItem: PhotoUI,
            newItem: PhotoUI
        ) =
            oldItem == newItem

        override fun areItemsTheSame(
            oldItem: PhotoUI,
            newItem: PhotoUI
        ) =
            oldItem.hashCode() == newItem.hashCode()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoVH {
        val binding =
            ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoVH(binding)
    }

    override fun onBindViewHolder(holder: PhotoVH, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }
}

