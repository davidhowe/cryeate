package com.davidhowe.cryeate.repositories.dao

import androidx.room.Dao
import androidx.room.Query
import com.davidhowe.cryeate.models.db.Coin
import io.reactivex.Single

@Dao
interface CoinDao : BaseDao<Coin> {
    @Query("SELECT * FROM Coin")
    fun getAllEntries(): Single<List<Coin>>

    @Query("SELECT * FROM Coin WHERE id = :coinGeckoId")
    fun getEntry(coinGeckoId: String): Single<Coin>


}