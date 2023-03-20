package com.yashkasera.flickrgallery.data

import androidx.paging.*
import com.yashkasera.flickrgallery.data.entity.Photo
import com.yashkasera.flickrgallery.data.source.PhotosRemoteMediator
import com.yashkasera.flickrgallery.data.source.local.FlickrDatabase
import com.yashkasera.flickrgallery.data.source.remote.ApiHelper
import com.yashkasera.flickrgallery.data.source.remote.PhotoSearchNetworkSource
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

    fun getPhotoSearchResult(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            )
        ) {
            PhotoSearchNetworkSource(apiHelper, query)
        }.flow
}