package com.davidhowe.cryeate.ui.coindetail

sealed class CoinDetailStateUI {
    data class Loading(val loading: Boolean) : CoinDetailStateUI()
    data class ChartData(val priceData: List<List<Double>>, val currencySymbol: String): CoinDetailStateUI()
}