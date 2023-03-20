package com.yashkasera.flickrgallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yashkasera.flickrgallery.databinding.ItemNetworkStateBinding


class LoadingStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        val shimmerImage = holder.binding.shimmerViewContainer
        val retryBtn = holder.binding.retyBtn
        val txtErrorMessage = holder.binding.errorMsgItem

        if (loadState is LoadState.Loading) {
            shimmerImage.isVisible = true
            txtErrorMessage.isVisible = false
            retryBtn.isVisible = false
        } else {
            shimmerImage.isVisible = false
        }

        if (loadState is LoadState.Error) {
            txtErrorMessage.isVisible = true
            retryBtn.isVisible = true
            txtErrorMessage.text = loadState.error.localizedMessage
        }

        retryBtn.setOnClickListener {
            retry.invoke()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            ItemNetworkStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    inner class LoadStateViewHolder(val binding: ItemNetworkStateBinding) :
        RecyclerView.ViewHolder(binding.root)
}