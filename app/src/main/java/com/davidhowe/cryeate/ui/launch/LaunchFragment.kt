package com.davidhowe.cryeate.ui.launch

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import com.davidhowe.cryeate.R
import com.davidhowe.cryeate.base.BaseFragment
import com.davidhowe.cryeate.databinding.FragmentCoinDetailBinding
import com.davidhowe.cryeate.databinding.FragmentLaunchBinding
import com.davidhowe.cryeate.di.AppComponent
import kotlinx.android.synthetic.main.fragment_launch.*

class LaunchFragment : BaseFragment<LaunchViewModel>() {

    lateinit var binding: FragmentLaunchBinding
    override val viewModelClass = LaunchViewModel::class.java
    override fun inject(appComponent: AppComponent) = appComponent.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentLaunchBinding.inflate(inflater, container, false)
        with(binding) {
            varViewModel = viewModel
            lifecycleOwner = this@LaunchFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        runHeaderAnimations()

        prog_loading.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(2500L)
                .setListener(null)
        }
    }

    private fun runHeaderAnimations() {
        ObjectAnimator.ofFloat(tv_header, "translationY", 30f).apply {
            duration = 2500
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }

        tv_desc.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(2500L)
                .setListener(null)
        }
    }
}