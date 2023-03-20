package com.yashkasera.flickrgallery.ui.search

import androidx.paging.PagingData
import com.yashkasera.flickrgallery.base.BaseViewModel
import com.yashkasera.flickrgallery.data.Repository
import com.yashkasera.flickrgallery.data.entity.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(repository: Repository) : BaseViewModel() {
    override val photosFlow: Flow<PagingData<Photo>>
        get() = TODO("Not yet implemented")
}