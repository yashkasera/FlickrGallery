package com.yashkasera.flickrgallery.base

import android.view.MotionEvent
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.yashkasera.flickrgallery.adapter.LoadingStateAdapter
import com.yashkasera.flickrgallery.adapter.PhotoAdapter
import com.yashkasera.flickrgallery.data.entity.Photo
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel : ViewModel() {
    abstract val photosFlow: Flow<PagingData<Photo>>
    val photoAdapter: PhotoAdapter = PhotoAdapter()
    val loadingStateAdapter: LoadingStateAdapter = LoadingStateAdapter { photoAdapter.retry() }
    val isRefreshing = ObservableBoolean(false)

    val onItemClick: (v: View, event: MotionEvent, photo: Photo) -> Boolean = { v, event, photo ->
        true
    }

    fun refresh() {
        isRefreshing.set(true)
        photoAdapter.refresh()
    }
}