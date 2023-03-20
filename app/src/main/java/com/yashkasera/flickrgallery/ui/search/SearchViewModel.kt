package com.yashkasera.flickrgallery.ui.search

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.yashkasera.flickrgallery.base.BaseViewModel
import com.yashkasera.flickrgallery.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@FlowPreview
@ExperimentalPagingApi
class SearchViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    val query = MutableStateFlow("")
    private val queryLiveData = MutableLiveData("")
    val isSearching = ObservableBoolean(false)
    val isQueryEmpty = ObservableBoolean(true)
    val isListEmpty = ObservableBoolean(false)

    init {
        photoAdapter.apply {
            addLoadStateListener {
                (it.refresh is LoadState.Loading).let { isLoading ->
                    isSearching.set(isLoading)
                    isListEmpty.set(isLoading.not() && itemCount == 0 && query.value.isNotEmpty())
                }
            }
        }
    }

    override val photosFlow = queryLiveData.switchMap {
        if (it.isNotEmpty()) {
            isSearching.set(true)
            repository.getPhotoSearchResult(it).asLiveData()
        } else null
    }.asFlow()

    init {
        viewModelScope.launch {
            query.debounce(1000)
                .distinctUntilChanged()
                .flowOn(Dispatchers.Main)
                .collect {
                    isQueryEmpty.set(it.isEmpty())
                    if (it.isNotEmpty())
                        queryLiveData.value = it
                    else {
                        photoAdapter.submitData(PagingData.empty())
                    }
                }
        }
    }
}