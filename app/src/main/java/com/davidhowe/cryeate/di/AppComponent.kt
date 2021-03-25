package com.davidhowe.cryeate.di

import com.davidhowe.cryeate.App
import com.davidhowe.cryeate.ui.coindetail.CoinDetailFragment
import com.davidhowe.cryeate.ui.coindetail.CoinDetailViewModel
import com.davidhowe.cryeate.ui.launch.LaunchFragment
import com.davidhowe.cryeate.ui.launch.LaunchViewModel
import com.davidhowe.cryeate.ui.main.MainFragment
import com.davidhowe.cryeate.ui.main.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent  {
    fun inject(app: App)
    fun inject(launchFragment: LaunchFragment)
    fun inject(launchViewModel: LaunchViewModel)
    fun inject(mainFragment: MainFragment)
    fun inject(mainViewModel: MainViewModel)
    fun inject(coinDetailFragment: CoinDetailFragment)
    fun inject(coinDetailViewModel: CoinDetailViewModel)
}