package com.yashkasera.flickrgallery.data.source.remote

import retrofit2.Response

interface ApiHelper {
    suspend fun getRecentPhotos(page: Int): Response<PhotosResponse>
    suspend fun searchPhoto(page: Int, query: String): Response<PhotosResponse>
}