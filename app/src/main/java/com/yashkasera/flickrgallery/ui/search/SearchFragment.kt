package com.yashkasera.flickrgallery.ui.search

import androidx.fragment.app.viewModels
import com.yashkasera.flickrgallery.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment() {
    override val viewModel by viewModels<SearchViewModel>()

    override fun initViewBinding() {

    }

    override fun setObservers() {
    }

}