package com.davidhowe.cryeate.ui.coindetail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.davidhowe.cryeate.base.BaseFragment
import com.davidhowe.cryeate.databinding.FragmentCoinDetailBinding
import com.davidhowe.cryeate.di.AppComponent
import kotlinx.android.synthetic.main.fragment_coin_detail.*

class CoinDetailFragment : BaseFragment<CoinDetailViewModel>() {

    private val args: CoinDetailFragmentArgs by navArgs()
    lateinit var binding: FragmentCoinDetailBinding
    override val viewModelClass = CoinDetailViewModel::class.java
    override fun inject(appComponent: AppComponent) = appComponent.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        with(binding) {
            varViewModel = viewModel
            lifecycleOwner = this@CoinDetailFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        line_chart.setNoDataText("")
        line_chart.setNoDataTextColor(Color.TRANSPARENT)
        line_chart.invalidate()
        viewModel.load(args.coinId)
    }
}