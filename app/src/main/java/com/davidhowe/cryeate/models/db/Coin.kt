package com.davidhowe.cryeate.models.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.davidhowe.cryeate.models.network.GetCoinMarketDataResponse
import timber.log.Timber
import java.text.DecimalFormat

@Entity
class Coin (
    @PrimaryKey val id: String,
    @ColumnInfo(name = "symbol") var symbol: String="",
    @ColumnInfo(name = "name") var name: String="",
    @ColumnInfo(name = "price") var price: Double=0.0,
    @ColumnInfo(name = "price_symbol") var priceSymbol: String="",
    @ColumnInfo(name = "image_remote") var imageRemote: String="",
    @ColumnInfo(name = "market_cap") var marketCap: Int=0,
    @ColumnInfo(name = "market_cap_rank") var marketCapRank: Int=0,
    @ColumnInfo(name = "high_24h") var high24h: Int=0,
    @ColumnInfo(name = "low_24h") var low24h: Int=0,
    @ColumnInfo(name = "price_change_percentage_24h") var priceChangePercentage24h: Double=0.0
) {
    fun updateFromNetwork(networkModel : GetCoinMarketDataResponse, priceSymbol: String) {
        this.symbol = networkModel.symbol
        this.name = networkModel.name
        this.price = networkModel.current_price
        this.priceSymbol = priceSymbol
        this.imageRemote = networkModel.image
        this.marketCap = 0//networkModel.market_cap todo
        this.marketCapRank = 0//networkModel.market_cap_rank todo
        this.high24h = networkModel.high_24h.toInt()
        this.low24h = networkModel.low_24h.toInt()
        this.priceChangePercentage24h = networkModel.price_change_percentage_24h
    }

    fun getFormattedPrice() : String {
        return priceSymbol+DecimalFormat("#0.00").format(price)
    }
}