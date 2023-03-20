package com.yashkasera.flickrgallery.data.source.remote

import retrofit2.Response
import javax.inject.Inject


class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getRecentPhotos(page: Int): Response<PhotosResponse> =
        apiService.getPhotos(method = "flickr.photos.getRecent", page = page)

    override suspend fun searchPhoto(page: Int, query: String): Response<PhotosResponse> =
        apiService.getPhotos(method = "flickr.photos.search", page = page, text = query)
}