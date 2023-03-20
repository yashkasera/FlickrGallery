package com.yashkasera.flickrgallery.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yashkasera.flickrgallery.base.BaseFragment
import com.yashkasera.flickrgallery.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    override val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initViewBinding() {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    override fun setObservers() {
        lifecycleScope.launch {
            viewModel.photosFlow.collectLatest {
                viewModel.photoAdapter.submitData(it)
                viewModel.isRefreshing.set(false)
            }
        }
    }
}