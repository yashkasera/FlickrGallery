package com.yashkasera.flickrgallery.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.yashkasera.flickrgallery.data.entity.Photo
import com.yashkasera.flickrgallery.data.entity.PhotoRemoteKey
import com.yashkasera.flickrgallery.data.source.local.FlickrDatabase
import com.yashkasera.flickrgallery.data.source.remote.ApiHelper

const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class PhotosRemoteMediator(
    private val apiHelper: ApiHelper,
    private val database: FlickrDatabase
) : RemoteMediator<Int, Photo>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Photo>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val remoteKey = database.photoRemoteKeyDao().getRemoteKeyForLastItem()
                remoteKey?.nextPage ?: STARTING_PAGE_INDEX
            }
        }
        try {
            val apiResponse = apiHelper.getRecentPhotos(page)
            val photoList: List<Photo> = apiResponse.body()?.photoWrapper?.photo ?: emptyList()
            val endOfPaginationReached = apiResponse.body()?.photoWrapper?.pages == page

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.photoDao().clear()
                    database.photoRemoteKeyDao().clear()
                }
                database.photoDao().insertAll(photoList)
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (photoList.isEmpty()) null else page + 1
                val keys = photoList.map {
                    PhotoRemoteKey(
                        id = it.id,
                        prevPage = prevKey,
                        nextPage = nextKey,
                    )
                }
                database.photoRemoteKeyDao().insertAll(keys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }
}