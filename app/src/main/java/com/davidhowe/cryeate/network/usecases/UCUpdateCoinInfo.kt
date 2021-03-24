package com.davidhowe.cryeate.network.usecases

import com.davidhowe.cryeate.Config
import com.davidhowe.cryeate.models.db.Coin
import com.davidhowe.cryeate.models.network.GetCoinMarketDataResponse
import com.davidhowe.cryeate.network.CoinGeckoAPI
import com.davidhowe.cryeate.repositories.usecases.UCRepoCoin
import com.davidhowe.cryeate.repositories.usecases.UCRepoProperties
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class UCUpdateCoinInfo @Inject constructor(private val api: CoinGeckoAPI, private val repoProperties: UCRepoProperties, private val repoCoin: UCRepoCoin) {
    fun execute() : Single<Boolean> {
        val currency = "usd" //todo adjust based on device locale
        val idsList = Config.COIN.values().map {Config.getCoinGeckoId(it)}.joinToString(separator = ",")
        val url = "coins/markets?vs_currency=$currency&ids=$idsList&order=market_cap_desc&per_page=10&page=1&sparkline=false"
        Timber.d("url = $url")
        return api.getCoinsMarketInfo(url = url)
            .flatMap { serverResponse ->
                Timber.d("serverResponse=$serverResponse")
                if(serverResponse.isSuccessful) {
                    //todo update all fields in dB
                    val networkList = serverResponse.body()

                    val dbCoins = mutableListOf<Coin>()

                    repoCoin.getAllEntries()
                            .flatMap { dbCoinList ->
                                Timber.d("dbCoinList before =$dbCoinList")
                                networkList?.forEach { networkCoin ->
                                    val dbCoin = dbCoinList.firstOrNull { it.id==networkCoin.id } ?: Coin(networkCoin.id)
                                    dbCoin.updateFromNetwork(networkCoin, "$") //todo set price symbol depending on Local
                                    dbCoins.add(dbCoin)
                                }

                                repoCoin.updateCoins(dbCoins)
                                        .toSingle {}
                                        .flatMap {
                                            repoProperties.setLastAPIPricesRetrieved(System.currentTimeMillis())
                                                    .toSingle {
                                                        true
                                                    }
                                        }
                            }
                }
                else
                   Single.just(false)
            }
            .subscribeOn(Schedulers.io())
    }
}