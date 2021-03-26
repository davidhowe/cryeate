package com.davidhowe.cryeate.ui.coindetail

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.davidhowe.cryeate.App
import com.davidhowe.cryeate.base.BaseFragment
import com.davidhowe.cryeate.base.BaseStateUI
import com.davidhowe.cryeate.base.BaseViewModel
import com.davidhowe.cryeate.extensions.SingleLiveEvent
import com.davidhowe.cryeate.network.usecases.UCFetchCoinHistory
import com.davidhowe.cryeate.repositories.usecases.UCRepoCoin
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CoinDetailViewModel @Inject constructor(
        application: App,
        val ucRepoCoin: UCRepoCoin,
        val ucFetchCoinHistory: UCFetchCoinHistory
) : BaseViewModel(application) {

    private val _baseViewState = MutableLiveData<CoinDetailStateUI>().apply {
        value = CoinDetailStateUI.Loading(true)
    }
    val baseViewState: LiveData<CoinDetailStateUI> = _baseViewState

    private val _coinName = MutableLiveData<String>().apply {
        value = ""
    }
    val coinName: LiveData<String> = _coinName

    @SuppressLint("CheckResult")
    /**
     * Process:
     * 1. Fetches coin from local db repo
     * 2. Attempts network call to retrieve coin history from api
     * 3. Presents coin history if success OR shows error messages if fail
     */
    override fun load(id: String) {
        Timber.d("Load : coinId=$id")

        ucRepoCoin.getEntry(id)
                .flatMap {
                    _coinName.postValue(it.name)
                    ucFetchCoinHistory.execute(id)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ it ->
                    if(it.first && it.second!=null) {
                        Timber.d("Valid network data to return")
                        _baseViewState.postValue(CoinDetailStateUI.Loading(false))
                        Single.timer(200, TimeUnit.MILLISECONDS)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe { _->
                                    _baseViewState.postValue(CoinDetailStateUI.ChartData(it.second!!, "$")) //todo update with generic currency symbol
                                }
                    }
                    else {
                        handleNetworkError()
                    }
                }, {
                    handleNetworkError()
                })
    }

    /**
     * Presents generic network error dialog, progresses back on user tap
     */
    private fun handleNetworkError() {
        liveDataEvent.postValue(BaseStateUI.ErrorDialog(
            errorState = BaseStateUI.ErrorStates.NETWORK_ERROR,
            listener = WeakReference(
                    object : BaseFragment.DialogClickListener {
                        override fun onPosClicked() {
                            liveDataEvent.postValue(BaseStateUI.Back)
                        }
                    }
            )
        ))
    }

    fun onBackPressed() {
        liveDataEvent.postValue(BaseStateUI.Back)
    }
}