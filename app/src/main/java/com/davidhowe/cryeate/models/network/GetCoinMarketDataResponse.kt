package com.davidhowe.cryeate.models.network

class GetCoinMarketDataResponse(
    val id: String,
    val symbol: String,
    val price: Int,
    val image: String,
    val market_cap: Int,
    val market_cap_rank: Int,
    val high_24h: Int,
    val low_24h: Int,
    val price_change_percentage_24h: Double
)