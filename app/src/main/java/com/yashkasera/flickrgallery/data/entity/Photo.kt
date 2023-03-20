package com.yashkasera.flickrgallery.data.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Photo(
    @SerializedName("id") @PrimaryKey val id: String,
    @SerializedName("title") val title: String?,
    @SerializedName("url_s") val urlS: String?,
    @SerializedName("height_s") val heightS: Int?,
    @SerializedName("width_s") val widthS: Int?
)