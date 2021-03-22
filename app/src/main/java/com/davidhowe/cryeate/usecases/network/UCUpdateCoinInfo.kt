package com.davidhowe.cryeate.usecases.network

import com.davidhowe.cryeate.Config
import com.davidhowe.cryeate.models.network.GetCoinMarketDataResponse
import com.davidhowe.cryeate.network.CoinGeckoAPI
import com.davidhowe.cryeate.usecases.database.UCRepoProperties
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class UCUpdateCoinInfo @Inject constructor(private val api: CoinGeckoAPI, private val repoProperties: UCRepoProperties) {
    fun execute() : Single<GetCoinMarketDataResponse?> {
        val currency = "usd" //todo adjust based on device locale
        val idsList = Config.COIN.values().map {Config.getCoinGeckoId(it)}.joinToString(separator = "%2")
        val url = "coins/markets?vs_currency=$currency&ids=$idsList&order=market_cap_desc&per_page=10&page=1&sparkline=false"
        Timber.d("url = $url")
        return api.getCoinsMarketInfo(url = url)
            .map { serverResponse ->
                if(serverResponse.isSuccessful) {
                    repoProperties.setLastAPIPricesRetrieved(System.currentTimeMillis())



                }
                serverResponse.body()
            }
            .subscribeOn(Schedulers.io())
    }
}