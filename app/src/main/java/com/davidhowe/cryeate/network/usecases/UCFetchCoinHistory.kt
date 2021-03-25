package com.davidhowe.cryeate.network.usecases

import com.davidhowe.cryeate.network.CoinGeckoAPI
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class UCFetchCoinHistory @Inject constructor(private val api: CoinGeckoAPI) {
    fun execute(coinId: String) : Single<Pair<Boolean, List<List<Double>>?>> {
        val currency = "usd" //todo adjust based on device locale
        val url = "coins/$coinId/market_chart?vs_currency=$currency&days=7&interval=daily"
        Timber.d("url = $url")
        return api.getCoinMarketHistory(url = url)
            .flatMap { serverResponse ->
                Timber.d("serverResponse=$serverResponse")
                if(serverResponse.isSuccessful) {
                    Single.just(Pair(true, serverResponse.body()?.prices))
                }
                else
                   Single.just(Pair(false, null))
            }
            .subscribeOn(Schedulers.io())
    }
}