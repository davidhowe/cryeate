package com.davidhowe.cryeate.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.davidhowe.cryeate.App
import com.davidhowe.cryeate.base.BaseViewModel
import com.davidhowe.cryeate.extensions.SingleLiveEvent
import com.davidhowe.cryeate.repositories.usecases.UCRepoCoin
import com.davidhowe.cryeate.repositories.usecases.UCRepoProperties
import com.davidhowe.cryeate.utils.CustomFormatter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import org.joda.time.DateTime
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor(
        application: App,
        val ucRepoCoin: UCRepoCoin,
        val ucRepoProperties: UCRepoProperties
) : BaseViewModel(application) {

    val uiLiveDataEvent = SingleLiveEvent<MainStateUI>()

    @SuppressLint("CheckResult")
    override fun load() {
        ucRepoCoin.getAllEntries()
            .subscribe { coinList ->
                Timber.d("coinList size = ${coinList.size}")
                ucRepoProperties.getLastAPIPricesRetrieved()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { it ->
                        uiLiveDataEvent.postValue(MainStateUI.CoinList(
                            coinList = coinList,
                            lastUpdated = DateTime(it).toLocalDateTime().toString(CustomFormatter.getLastUpdatedPattern())
                        ))
                    }

            }
    }

}