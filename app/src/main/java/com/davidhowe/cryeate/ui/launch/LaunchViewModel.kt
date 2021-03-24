package com.davidhowe.cryeate.ui.launch

import android.annotation.SuppressLint
import com.davidhowe.cryeate.App
import com.davidhowe.cryeate.Config.MAX_API_LAST_ACTIVE
import com.davidhowe.cryeate.Config.MAX_API_PRICES_LAST_RETRIEVED
import com.davidhowe.cryeate.base.BaseStateUI
import com.davidhowe.cryeate.base.BaseViewModel
import com.davidhowe.cryeate.network.usecases.UCUpdateCoinInfo
import com.davidhowe.cryeate.repositories.SharedPrefsRepo
import com.davidhowe.cryeate.repositories.usecases.UCRepoProperties
import com.davidhowe.cryeate.network.usecases.UCUpdateServerStatus
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LaunchViewModel @Inject constructor(
        application: App,
        val ucUpdateServerStatus: UCUpdateServerStatus,
        val ucUpdateCoinInfo: UCUpdateCoinInfo,
        val ucRepoProperties: UCRepoProperties,
        val sharedPrefsRepo: SharedPrefsRepo
    ) : BaseViewModel(application) {

    private enum class STEP {CHECK_ACTIVE, FETCH_COIN_INFO, TRANSITION}

    private var currentStep = STEP.CHECK_ACTIVE

    var timeSinceLaunch = System.currentTimeMillis()
    var compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun load() {
        runDBDefaultData().subscribe {
            Timber.d("Inserted default data")
            sharedPrefsRepo.setFirstLaunch(false)
            doNextStep()
        }
    }

    @SuppressLint("CheckResult")
    private fun doNextStep() {
        Timber.d("doNextStep()")

        var lastApiActive = 0L
        var lastApiPricesRetrieved = 0L

        ucRepoProperties.getLastAPIActive()
            .flatMap {
                lastApiActive = it
                Timber.d("lastApiActive=$lastApiActive")
                ucRepoProperties.getLastAPIPricesRetrieved()
            }
            .subscribe { result ->
                lastApiPricesRetrieved = result
                Timber.d("lastApiPricesRetrieved=$lastApiPricesRetrieved")
                currentStep = when {
                    System.currentTimeMillis() - lastApiActive > MAX_API_LAST_ACTIVE -> {
                        STEP.CHECK_ACTIVE
                    }
                    System.currentTimeMillis() - lastApiPricesRetrieved > MAX_API_PRICES_LAST_RETRIEVED -> {
                        STEP.FETCH_COIN_INFO
                    }
                    else -> {
                        STEP.TRANSITION
                    }
                }
                Timber.d("currentStep=$currentStep")

                actionCurrentStep()
            }
    }

    /**
     * Possible steps:
     * 1. Fetch CoinGecko server status
     * 2. Fetch coin info for user interested coins
     * 3. Transition to Main Fragment
     *
     * Routine runs for a maximum of 8 seconds before timing out and transitioning to next view
     */
    private fun actionCurrentStep() {
        Timber.d("actionCurrentStep()")

        when(currentStep) {
            STEP.CHECK_ACTIVE -> {
                compositeDisposable.addAll(ucUpdateServerStatus.execute()
                    .subscribe { result ->
                        Timber.d("CHECK_ACTIVE result=$result")
                        doNextStep()
                    })
            }
            STEP.FETCH_COIN_INFO -> {
                compositeDisposable.addAll(ucUpdateCoinInfo.execute()
                    .subscribe { result ->
                        Timber.d("FETCH_COIN_INFO result=$result")
                        doNextStep()
                    })
            }
            STEP.TRANSITION -> liveDataEvent.postValue(BaseStateUI.To(LaunchFragmentDirections.actionLaunchFragmentToMainFragment()))
        }
    }

    //todo move to seperate class
    private fun runDBDefaultData() : Completable {
        Timber.d("runDBDefaultData")

        return if(sharedPrefsRepo.isFirstLaunch()) {
            ucRepoProperties.setDefaultEntry()
        } else {
            Single.just(1).ignoreElement()
        }
    }
}