package com.yashkasera.flickrgallery.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yashkasera.flickrgallery.data.entity.Photo
import com.yashkasera.flickrgallery.databinding.ItemPhotoBinding
import com.yashkasera.flickrgallery.util.loadImage

class PhotoAdapter() :
    PagingDataAdapter<Photo, PhotoAdapter.PhotoViewHolder>(PhotoDiffCallback()) {

    private var touchListener: ((v: View, event: MotionEvent, photo: Photo) -> Boolean)? = null

    fun setOnTouchListener(touchListener: (v: View, event: MotionEvent, photo: Photo) -> Boolean) {
        this.touchListener = touchListener
    }

    private class PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: PhotoAdapter.PhotoViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoAdapter.PhotoViewHolder {
        return PhotoViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class PhotoViewHolder(private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ClickableViewAccessibility")
        fun bind(data: Photo) {
            data.urlS?.let { binding.imageView.loadImage(it) }
            data.title?.let { binding.textView.text = it }
            binding.root.setOnTouchListener { v, event -> touchListener?.invoke(v, event, data) ?: true }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    companion object {
        const val VIEW_TYPE_ITEM = 1
        const val VIEW_TYPE_LOADING = 2
    }
}