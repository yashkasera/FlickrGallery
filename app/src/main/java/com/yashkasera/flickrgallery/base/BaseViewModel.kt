package com.yashkasera.flickrgallery.base

import android.os.Message
import android.view.MotionEvent
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.yashkasera.flickrgallery.adapter.LoadingStateAdapter
import com.yashkasera.flickrgallery.adapter.PhotoAdapter
import com.yashkasera.flickrgallery.data.entity.Photo
import com.yashkasera.flickrgallery.util.NETWORK_ERROR
import com.yashkasera.flickrgallery.util.OPEN_PHOTO
import com.yashkasera.flickrgallery.util.SingleEvent
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel : ViewModel() {
    protected var message = Message()
    val singleLiveEvent = MutableLiveData<SingleEvent>()
    abstract val photosFlow: Flow<PagingData<Photo>>
    val isRefreshing = ObservableBoolean(false)

    val photoAdapter: PhotoAdapter = PhotoAdapter().apply {
        addLoadStateListener {
            if (it.refresh is LoadState.Error) {
                singleLiveEvent.value = SingleEvent(Message().apply {
                    what = NETWORK_ERROR
                    obj = (it.refresh as LoadState.Error).error
                })
            }
        }
    }
    val loadingStateAdapter: LoadingStateAdapter = LoadingStateAdapter { photoAdapter.retry() }

    val onItemClick: (v: View, event: MotionEvent, photo: Photo) -> Boolean = { v, event, photo ->
        message.what = OPEN_PHOTO
        message.obj = photo
        singleLiveEvent.value = SingleEvent(message)
        true
    }

    fun refresh() {
        isRefreshing.set(true)
        photoAdapter.refresh()
    }
}