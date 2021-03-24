package com.davidhowe.cryeate.ui.main

import com.davidhowe.cryeate.models.db.Coin

sealed class MainStateUI {
    data class CoinList(val coinList: List<Coin>): MainStateUI()
}