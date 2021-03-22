package com.davidhowe.cryeate.usecases.network

import com.davidhowe.cryeate.network.CoinGeckoAPI
import com.davidhowe.cryeate.usecases.database.UCRepoProperties
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UCUpdateServerStatus @Inject constructor(private val api: CoinGeckoAPI, private val repoProperties: UCRepoProperties) {
    fun execute() : Single<Boolean> {
        return api.getServerStatus()
            .map { serverResponse ->
                if(serverResponse.isSuccessful) {
                    repoProperties.setLastAPIActive(System.currentTimeMillis())
                }
                serverResponse.isSuccessful
            }
            .subscribeOn(Schedulers.io())
    }
}