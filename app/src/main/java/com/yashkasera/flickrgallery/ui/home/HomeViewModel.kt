package com.yashkasera.flickrgallery.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yashkasera.flickrgallery.base.BaseViewModel
import com.yashkasera.flickrgallery.data.Repository
import com.yashkasera.flickrgallery.data.entity.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: Repository) : BaseViewModel() {
    val photos: MutableLiveData<List<Photo>> = MutableLiveData()

    init {
        viewModelScope.launch {
            photos.value = repository.getPhotos().getOrNull() ?: emptyList()
        }
    }
}