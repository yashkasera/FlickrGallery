package com.yashkasera.flickrgallery.data.source.remote


import com.google.gson.annotations.SerializedName
import com.yashkasera.flickrgallery.data.entity.Photo

data class PhotosResponse(
    @SerializedName("photos") val photoWrapper: PhotoWrapper,
    @SerializedName("stat") val stat: String?
)

data class PhotoWrapper(
    @SerializedName("page") val page: Int?,
    @SerializedName("pages") val pages: Int?,
    @SerializedName("perpage") val perpage: Int?,
    @SerializedName("photo") val photo: List<Photo>,
    @SerializedName("total") val total: Int?
)