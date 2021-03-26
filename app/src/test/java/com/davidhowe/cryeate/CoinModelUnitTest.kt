package com.davidhowe.cryeate

import com.davidhowe.cryeate.models.db.Coin
import com.davidhowe.cryeate.models.network.GetCoinMarketDataResponse
import com.davidhowe.cryeate.utils.CustomFormatter
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CoinModelUnitTest {

    @Test
    fun networkupdating_isCorrect() {
        val coin = Coin("testcoin1")
        coin.updateFromNetwork(
            networkModel = GetCoinMarketDataResponse(
                "testcoin1",
                "tstc1",
                "testcoin1",
                500.5,
                "url_image",
                505.3,
                420.2,
                4.8,
                "2021-05-26",
            ),
            priceSymbol = "$"
        )

        assertEquals(500.5, coin.price, 0.0)
        assertEquals(505, coin.high24h)
        assertEquals(420, coin.low24h)
        assertEquals("url_image", coin.imageRemote)
        assertEquals(4.8, coin.priceChangePercentage24h, 0.0)
        assertEquals("2021-05-26", coin.serverTimestamp)
    }
}