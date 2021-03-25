package com.davidhowe.cryeate.ui.main

import android.animation.ObjectAnimator
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.lifecycle.Observer
import com.davidhowe.cryeate.base.BaseFragment
import com.davidhowe.cryeate.databinding.FragmentMainBinding
import com.davidhowe.cryeate.di.AppComponent
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber

class MainFragment : BaseFragment<MainViewModel>() {

    lateinit var binding: FragmentMainBinding
    lateinit var adapter: CoinAdapter
    override val viewModelClass = MainViewModel::class.java
    override fun inject(appComponent: AppComponent) = appComponent.inject(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentMainBinding.inflate(inflater, container, false)
        adapter = CoinAdapter()
        binding.coinList.adapter = adapter
        with(binding) {
            varViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.uiLiveDataEvent.observe(
            viewLifecycleOwner,
            mainStateUIObserver
        )
        super.onViewCreated(view, savedInstanceState)
        runHeaderAnimations()
    }

    private fun runHeaderAnimations() {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape, dont aniomate since header views are removed
        } else {
            ObjectAnimator.ofFloat(tv_header, "translationY", 30f).apply {
                duration = 2500
                interpolator = AccelerateDecelerateInterpolator()
                start()
            }

            tv_desc?.apply {
                alpha = 0f
                visibility = View.VISIBLE
                animate()
                    .alpha(1f)
                    .setDuration(2500L)
                    .setListener(null)
            }
        }
    }

    private val mainStateUIObserver = Observer<MainStateUI> { state ->
        state?.let {
            when (state) {
                is MainStateUI.CoinList -> {
                    Timber.d("is MainStateUI.CoinList ->")
                    tv_desc_2?.text = state.lastUpdated
                    adapter.submitList(state.coinList)
                }
            }

        }
    }


}