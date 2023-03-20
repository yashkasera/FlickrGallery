package com.yashkasera.flickrgallery.util

import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textfield.TextInputEditText
import com.yashkasera.flickrgallery.adapter.LoadingStateAdapter
import com.yashkasera.flickrgallery.adapter.PhotoAdapter
import com.yashkasera.flickrgallery.data.entity.Photo
import kotlinx.coroutines.flow.MutableStateFlow

@BindingAdapter("photoAdapter", "stateAdapter", "onPhotoItemClick")
fun setPhotoAdapter(
    view: RecyclerView,
    adapter: PhotoAdapter,
    stateAdapter: LoadingStateAdapter,
    function: (v: View, event: MotionEvent, photo: Photo) -> Boolean
) {
    view.layoutManager = GridLayoutManager(view.context, 3)
    view.setHasFixedSize(false)
    view.adapter = adapter.withLoadStateFooter(
        footer = stateAdapter
    )
    adapter.setOnTouchListener(function)
}


@BindingAdapter("onRefresh")
fun setOnRefreshListener(view: SwipeRefreshLayout, function: () -> Unit) {
    view.setOnRefreshListener { function() }
}

@BindingAdapter("isRefreshing")
fun setRefreshing(view: SwipeRefreshLayout, isRefreshing: Boolean) {
    view.isRefreshing = isRefreshing
}

@BindingAdapter("onSearch")
fun setOnSearch(view: TextInputEditText, query: MutableStateFlow<String>) {
    view.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            query.value = s.toString()
        }
    })
}