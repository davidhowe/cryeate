package com.davidhowe.cryeate.models.network

data class GetCoinHistoryDataResponse(
    val prices: List<List<Double>>
)