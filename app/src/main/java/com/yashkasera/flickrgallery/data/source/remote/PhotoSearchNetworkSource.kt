package com.yashkasera.flickrgallery.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yashkasera.flickrgallery.data.entity.Photo

class PhotoSearchNetworkSource(
    private val apiHelper: ApiHelper,
    private val query: String
) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> =
        try {
            val currentPageNo = params.key ?: 1
            val responseData = apiHelper.searchPhoto(currentPageNo, query)
            val data = responseData.body()?.photoWrapper?.photo?.map { it }
            LoadResult.Page(
                data!!,
                if (currentPageNo == 1) null else currentPageNo - 1,
                if (data.isEmpty()) null else currentPageNo + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
}