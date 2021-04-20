package com.davidhowe.cryeate.ui.main.watchlist

import com.davidhowe.cryeate.models.db.Coin

sealed class WatchListStateUI {
    data class CoinList(val coinList: List<Coin>, val lastUpdated: String): WatchListStateUI()
}