package com.davidhowe.cryeate.usecases.database

import com.davidhowe.cryeate.models.db.Properties
import com.davidhowe.cryeate.repositories.Database
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class UCRepoProperties @Inject constructor(private val roomDb: Database) {

    fun getLastAPIActive() : Single<Long> {
        return roomDb.propertiesDao().getEntry().map { it.apiLastActive }.subscribeOn(Schedulers.io())
    }

    fun getLastAPIPricesRetrieved() : Single<Long> {
        return roomDb.propertiesDao().getEntry().map { it.apiPricesLastRetrieved }.subscribeOn(Schedulers.io())
    }

    fun setLastAPIActive(timeMillis: Long) {
        roomDb.propertiesDao().getEntry().map {
            it.apiLastActive = timeMillis
            roomDb.propertiesDao().update(it)
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    fun setLastAPIPricesRetrieved(timeMillis: Long) {
        roomDb.propertiesDao().getEntry().map {
            it.apiPricesLastRetrieved = timeMillis
            roomDb.propertiesDao().update(it)
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    fun setDefaultEntry() : Completable {
        Timber.d("setDefaultEntry")
        return roomDb.propertiesDao().insert(Properties(
            uid = 1
        )).subscribeOn(Schedulers.io())
    }
}