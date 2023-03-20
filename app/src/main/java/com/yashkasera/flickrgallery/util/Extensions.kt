package com.yashkasera.flickrgallery.util

import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.yashkasera.flickrgallery.di.GlideApp

fun ImageView.loadImage(url: String) {
    val shimmer = Shimmer.AlphaHighlightBuilder()
        .setBaseAlpha(0.8f)
        .setHighlightAlpha(0.7f)
        .setDropoff(0.9f)
        .setIntensity(0.5f)
        .setAutoStart(true)
        .build()

    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }
    GlideApp.with(this)
        .load(url)
        .centerCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .placeholder(shimmerDrawable).into(this)
}