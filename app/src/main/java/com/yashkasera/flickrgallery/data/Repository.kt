package com.yashkasera.flickrgallery.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yashkasera.flickrgallery.data.entity.Photo
import com.yashkasera.flickrgallery.data.source.local.FlickrDatabase
import com.yashkasera.flickrgallery.data.source.remote.ApiHelper
import com.yashkasera.flickrgallery.util.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val database: FlickrDatabase
) {
    private val pagingSourceFactory = { database.photoDao().getAll() }

    @ExperimentalPagingApi
    fun getPhotos(): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = PhotosRemoteMediator(
                apiHelper,
                database
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }
}