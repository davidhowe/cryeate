package com.davidhowe.cryeate.network

import com.davidhowe.cryeate.models.network.GetCoinHistoryDataResponse
import com.davidhowe.cryeate.models.network.GetCoinMarketDataResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.davidhowe.cryeate.models.network.GetServerStatusResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

interface CoinGeckoAPI {

    companion object {
        private const val BASE_URL = "https://api.coingecko.com/api/v3/"

        fun create(): CoinGeckoAPI {
            val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(object : Interceptor {
                        @Throws(IOException::class)
                        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                            val request = chain.request()
                            val response = chain.proceed(request)
                            Timber.d("response:$response")
                            return response
                        }
                    })
                    .build()

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .build()

            return retrofit.create(CoinGeckoAPI::class.java)
        }
    }

    /*--------------- GET REQUESTS --------------- */

    @GET("ping")
    fun getServerStatus() : Single<Response<GetServerStatusResponse>>

    @GET//"coins/markets?vs_currency=zar&order=market_cap_desc&per_page=100&page=1&sparkline=false"
    fun getCoinsMarketInfo(@Url url: String) : Single<Response<List<GetCoinMarketDataResponse>>>

    @GET//"coins/bitcoin/market_chart?vs_currency=usd&days=7"
    fun getCoinMarketHistory(@Url url: String) : Single<Response<GetCoinHistoryDataResponse>>
}