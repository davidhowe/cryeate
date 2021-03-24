package com.davidhowe.cryeate.models.network

class GetCoinMarketDataResponse(
    val id: String,
    val symbol: String,
    val name: String,
    val current_price: Double,
    val image: String,
    val high_24h: Double,
    val low_24h: Double,
    val price_change_percentage_24h: Double
)