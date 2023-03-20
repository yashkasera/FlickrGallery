package com.yashkasera.flickrgallery.ui.search

import com.yashkasera.flickrgallery.base.BaseViewModel
import com.yashkasera.flickrgallery.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(repository: Repository) : BaseViewModel() {
}