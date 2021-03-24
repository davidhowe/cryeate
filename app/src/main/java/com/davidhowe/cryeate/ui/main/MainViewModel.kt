package com.davidhowe.cryeate.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.davidhowe.cryeate.App
import com.davidhowe.cryeate.base.BaseViewModel
import com.davidhowe.cryeate.extensions.SingleLiveEvent
import com.davidhowe.cryeate.repositories.usecases.UCRepoCoin
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
        application: App,
        val ucRepoCoin: UCRepoCoin
) : BaseViewModel(application) {

    val _isLoading = MutableLiveData(true) //To change in VM
    val isLoadingVB : LiveData<Boolean> = _isLoading  //For View binding use

    val uiLiveDataEvent = SingleLiveEvent<MainStateUI>()

    @SuppressLint("CheckResult")
    override fun load() {
        ucRepoCoin.getAllEntries()
                .subscribe { coinList ->
                    Timber.d("coinList size = ${coinList.size}")
                    _isLoading.postValue(false)
                    uiLiveDataEvent.postValue(MainStateUI.CoinList(coinList))
                }
    }

}