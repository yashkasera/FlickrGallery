package com.yashkasera.flickrgallery.util

import android.view.MotionEvent
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yashkasera.flickrgallery.adapter.LoadingStateAdapter
import com.yashkasera.flickrgallery.adapter.PhotoAdapter
import com.yashkasera.flickrgallery.data.entity.Photo

@BindingAdapter("photoAdapter", "stateAdapter", "onPhotoItemClick")
fun setPhotoAdapter(
    view: RecyclerView,
    adapter: PhotoAdapter,
    stateAdapter: LoadingStateAdapter,
    onTouchListener: (v: View, event: MotionEvent, photo: Photo) -> Boolean
) {
    view.layoutManager = GridLayoutManager(view.context, 3)
    view.setHasFixedSize(false)
    view.adapter = adapter.withLoadStateFooter(
        footer = stateAdapter
    )
    adapter.setOnTouchListener(onTouchListener)
}


@BindingAdapter("onRefresh")
fun setOnRefreshListener(view: SwipeRefreshLayout, function: () -> Unit) {
    view.setOnRefreshListener { function() }
}

@BindingAdapter("isRefreshing")
fun setRefreshing(view: SwipeRefreshLayout, isRefreshing: Boolean) {
    view.isRefreshing = isRefreshing
}