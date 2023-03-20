package com.yashkasera.flickrgallery.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yashkasera.flickrgallery.base.BaseFragment
import com.yashkasera.flickrgallery.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    override val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initViewBinding() {

    }

    override fun setObservers() {
        viewModel.photos.observe(viewLifecycleOwner) {
            Log.d("HomeFragment.kt", "YASH => setObservers:29 => $it")
        }
    }
}