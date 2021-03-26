package com.davidhowe.cryeate

import org.joda.time.Hours

object Config {
    const val MIN_LAUNCH_SCREEN_DURATION = 5000L

    val MAX_API_LAST_ACTIVE = Hours.TWO.toStandardDuration().millis //2 hours in millis
    val MAX_API_PRICES_LAST_RETRIEVED = Hours.ONE.toStandardDuration().millis //2 hours in millis

    enum class COIN {BITCOIN, ETHEREUM, LITECOIN, RIPPLE, MONERO, CARDANO, DOGECOIN}

    fun getCoinGeckoId(enumCoin: COIN) : String {
        return when(enumCoin) {
            COIN.BITCOIN -> "bitcoin"
            COIN.ETHEREUM -> "ethereum"
            COIN.LITECOIN -> "litecoin"
            COIN.RIPPLE -> "ripple"
            COIN.MONERO -> "monero"
            COIN.CARDANO -> "cardano"
            COIN.DOGECOIN -> "dogecoin"
        }
    }
}