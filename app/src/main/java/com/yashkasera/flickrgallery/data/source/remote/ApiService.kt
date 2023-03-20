package com.yashkasera.flickrgallery.data.source.remote

import com.yashkasera.flickrgallery.util.NETWORK_PAGE_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("services/rest/")
    suspend fun getPhotos(
        @Query("method") method: String,
        @Query("per_page") perPage: Int = NETWORK_PAGE_SIZE,
        @Query("page") page: Int = 1,
        @Query("extras") extras: String = "url_s",
        @Query("text") text: String = ""
    ): Response<PhotosResponse>
}