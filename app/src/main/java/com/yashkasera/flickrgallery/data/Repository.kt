package com.yashkasera.flickrgallery.data

import com.yashkasera.flickrgallery.data.entity.Photo
import com.yashkasera.flickrgallery.data.source.remote.ApiHelper
import javax.inject.Inject

class Repository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getPhotos(): Result<List<Photo>> {
        return try {
            val res = apiHelper.getRecentPhotos(1)
            if (res.isSuccessful)
                Result.success(res.body()?.photoWrapper?.photo ?: emptyList())
            else
                Result.failure(Exception(res.errorBody().toString()))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}