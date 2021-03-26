package com.davidhowe.cryeate.repositories.usecases

import com.davidhowe.cryeate.repositories.SharedPrefsRepo
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * This use case is responsible for assigning default data to room DB
 */
class UCDefaultDBData @Inject constructor(private val ucRepoProperties: UCRepoProperties, private val sharedPrefsRepo: SharedPrefsRepo) {
    fun execute() : Completable {
        sharedPrefsRepo.setFirstLaunch(false)
        return if(sharedPrefsRepo.isFirstLaunch()) {
            ucRepoProperties.setDefaultEntry()
        } else {
            Single.just(1).ignoreElement()
        }
    }
}