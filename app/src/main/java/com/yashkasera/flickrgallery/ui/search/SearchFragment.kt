package com.yashkasera.flickrgallery.ui.search

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.yashkasera.flickrgallery.R
import com.yashkasera.flickrgallery.base.BaseFragment
import com.yashkasera.flickrgallery.databinding.FragmentSearchBinding
import com.yashkasera.flickrgallery.util.NETWORK_ERROR
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@FlowPreview
@ExperimentalPagingApi
@AndroidEntryPoint
class SearchFragment : BaseFragment() {
    override val viewModel by viewModels<SearchViewModel>()

    private lateinit var binding: FragmentSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setObservers() {
        lifecycleScope.launch {
            viewModel.photosFlow.collectLatest {
                viewModel.isSearching.set(false)
                viewModel.photoAdapter.submitData(it)
            }
        }
        viewModel.singleLiveEvent.observe(viewLifecycleOwner) {
            it.getContentIfNotHandledOrReturnNull()?.let { msg ->
                when (msg.what) {
                    NETWORK_ERROR ->
                        Snackbar.make(
                            binding.root,
                            (msg.obj as? Error)?.localizedMessage ?: "An error occurred",
                            Snackbar.LENGTH_SHORT
                        ).setAction(getString(R.string.retry)) {
                            viewModel.photoAdapter.retry()
                        }.show()
                }
            }
        }
    }

    override fun initViewBinding() {
        binding.searchEditText.requestFocus()
        binding.viewModel = viewModel
        binding.executePendingBindings()
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    hideKeyboard(binding.root)
                }
            }

        })
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}