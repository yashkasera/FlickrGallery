package com.yashkasera.flickrgallery.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yashkasera.flickrgallery.base.BaseViewModel
import com.yashkasera.flickrgallery.data.Repository
import com.yashkasera.flickrgallery.data.entity.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: Repository
) : BaseViewModel() {

    override val photosFlow: Flow<PagingData<Photo>> =
        repository.getPhotos()
            .cachedIn(viewModelScope)
}