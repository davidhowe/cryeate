package com.davidhowe.cryeate.repositories.usecases

import com.davidhowe.cryeate.models.db.Coin
import com.davidhowe.cryeate.repositories.dao.Database
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class UCRepoCoin @Inject constructor(private val roomDb: Database) {
    fun getAllEntries() : Single<List<Coin>> {
        return roomDb.coinDao().getAllEntries().subscribeOn(Schedulers.io())
    }

    fun getEntry(coin: Coin) : Single<Coin> {
        return roomDb.coinDao().getEntry(coin.id).subscribeOn(Schedulers.io())
    }

    fun getEntry(id: String) : Single<Coin> {
        return roomDb.coinDao().getEntry(id).subscribeOn(Schedulers.io())
    }

    fun updateCoins(coinList: List<Coin>) : Completable {
        Timber.d("updateCoins")
        return roomDb.coinDao().insert(coinList).subscribeOn(Schedulers.io())
    }
}