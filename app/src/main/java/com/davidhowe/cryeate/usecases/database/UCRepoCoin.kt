package com.davidhowe.cryeate.usecases.database

import com.davidhowe.cryeate.models.db.Coin
import com.davidhowe.cryeate.repositories.Database
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UCRepoCoin @Inject constructor(private val roomDb: Database) {
    fun getAllEntries() : Single<List<Coin>> {
        return roomDb.coinDao().getAllEntries().subscribeOn(Schedulers.io())
    }

    fun getEntry(coin: Coin) : Single<Coin> {
        return roomDb.coinDao().getEntry(coin.id).subscribeOn(Schedulers.io())
    }
}