package com.davidhowe.cryeate.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.davidhowe.cryeate.base.BaseFragment
import com.davidhowe.cryeate.databinding.FragmentMainBinding
import com.davidhowe.cryeate.di.AppComponent
import timber.log.Timber

class MainFragment : BaseFragment<MainViewModel>() {

    lateinit var binding: FragmentMainBinding
    lateinit var adapter: CoinAdapter
    override val viewModelClass = MainViewModel::class.java
    override fun inject(appComponent: AppComponent) = appComponent.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentMainBinding.inflate(inflater, container, false)
        adapter = CoinAdapter()
        binding.coinList.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.uiLiveDataEvent.observe(
                viewLifecycleOwner,
                mainStateUIObserver
        )
        super.onViewCreated(view, savedInstanceState)
    }

    private val mainStateUIObserver = Observer<MainStateUI> { state ->
        state?.let {
            when (state) {
                is MainStateUI.CoinList -> {
                    Timber.d("is MainStateUI.CoinList ->")
                    adapter.submitList(state.coinList)
                }
            }

        }
    }
}