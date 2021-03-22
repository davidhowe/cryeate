package com.davidhowe.cryeate.models.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.davidhowe.cryeate.models.network.GetCoinMarketDataResponse

@Entity
class Coin (
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "symbol") var symbol: String,
    @ColumnInfo(name = "price") var price: Int,
    @ColumnInfo(name = "price_symbol") var priceSymbol: String,
    @ColumnInfo(name = "image_remote") var imageRemote: String,
    @ColumnInfo(name = "market_cap") var marketCap: Int,
    @ColumnInfo(name = "market_cap_rank") var marketCapRank: Int,
    @ColumnInfo(name = "high_24h") var high24h: Int,
    @ColumnInfo(name = "low_24h") var low24h: Int,
    @ColumnInfo(name = "price_change_percentage_24h") var priceChangePercentage24h: Double
) {
    fun updateFromNetwork(networkModel : GetCoinMarketDataResponse, priceSymbol: String) {
        this.id = networkModel.id
        this.symbol = networkModel.symbol
        this.price = networkModel.price
        this.priceSymbol = priceSymbol
        this.imageRemote = networkModel.image
        this.marketCap = networkModel.market_cap
        this.marketCapRank = networkModel.market_cap_rank
        this.high24h = networkModel.high_24h
        this.low24h = networkModel.low_24h
        this.priceChangePercentage24h = networkModel.price_change_percentage_24h
    }
}