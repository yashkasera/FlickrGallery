package com.yashkasera.flickrgallery.ui.home

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.yashkasera.flickrgallery.R
import com.yashkasera.flickrgallery.base.BaseFragment
import com.yashkasera.flickrgallery.databinding.FragmentHomeBinding
import com.yashkasera.flickrgallery.util.NETWORK_ERROR
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    override val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_search -> {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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
}