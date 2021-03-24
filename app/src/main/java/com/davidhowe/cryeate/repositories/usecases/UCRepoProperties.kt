package com.davidhowe.cryeate.repositories.usecases

import com.davidhowe.cryeate.models.db.Properties
import com.davidhowe.cryeate.repositories.dao.Database
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class UCRepoProperties @Inject constructor(private val roomDb: Database) {

    fun getLastAPIActive() : Single<Long> {
        return roomDb.propertiesDao().getEntry().map {
            it.first().apiLastActive
        }.subscribeOn(Schedulers.io())
    }

    fun getLastAPIPricesRetrieved() : Single<Long> {
        return roomDb.propertiesDao().getEntry().map { it.first().apiPricesLastRetrieved }.subscribeOn(Schedulers.io())
    }

    fun setLastAPIActive(timeMillis: Long) : Completable {
        Timber.d("setLastAPIActive: $timeMillis")
        return roomDb.propertiesDao().getEntry().map {
            it.first().apiLastActive = timeMillis
            it.first()
        }.flatMapCompletable {
            roomDb.propertiesDao().update(it)
        }.subscribeOn(Schedulers.io())
    }

    fun setLastAPIPricesRetrieved(timeMillis: Long) : Completable {
        Timber.d("setLastAPIPricesRetrieved: $timeMillis")
        return roomDb.propertiesDao().getEntry().map {
            it.first().apiPricesLastRetrieved = timeMillis
            it.first()
        }.flatMapCompletable {
            roomDb.propertiesDao().update(it)
        }.subscribeOn(Schedulers.io())
    }

    fun setDefaultEntry() : Completable {
        Timber.d("setDefaultEntry")
        return roomDb.propertiesDao().insert(Properties(
            uid = 1,
            apiLastActive = 10L,
            apiPricesLastRetrieved = 0L
        )).subscribeOn(Schedulers.io())
    }
}