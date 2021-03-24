package com.davidhowe.cryeate.network.usecases

import com.davidhowe.cryeate.network.CoinGeckoAPI
import com.davidhowe.cryeate.repositories.usecases.UCRepoProperties
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class UCUpdateServerStatus @Inject constructor(private val api: CoinGeckoAPI, private val repoProperties: UCRepoProperties) {
    fun execute() : Single<Boolean> {
        Timber.d("execute()")
        return api.getServerStatus()
            .flatMap { serverResponse ->
                if(serverResponse.isSuccessful)
                    repoProperties.setLastAPIActive(System.currentTimeMillis())
                            .toSingle {
                                true
                            }
                else
                    Single.just(false)
            }
            .subscribeOn(Schedulers.io())
    }
}