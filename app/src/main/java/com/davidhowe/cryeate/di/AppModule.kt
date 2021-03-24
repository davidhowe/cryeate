package com.davidhowe.cryeate.di

import android.content.Context
import androidx.room.Room
import com.davidhowe.cryeate.App
import com.davidhowe.cryeate.network.CoinGeckoAPI
import com.davidhowe.cryeate.repositories.dao.Database
import com.davidhowe.cryeate.repositories.SharedPrefsRepo
import com.davidhowe.cryeate.repositories.usecases.UCRepoProperties
import com.davidhowe.cryeate.network.usecases.UCUpdateServerStatus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App, private val baseContext: Context) {

    @Provides
    @Singleton
    fun provideApplication(): App = app

    @Provides
    @Singleton
    fun provideContext(): Context = baseContext

    @Provides
    @Singleton
    fun provideDatabase(): Database {
        return Room.databaseBuilder(
            app.applicationContext,
            Database::class.java, "room-db-cryeate"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCoinGeckoAPI(): CoinGeckoAPI {
        return CoinGeckoAPI.create()
    }

    @Provides
    @Singleton
    fun provideSharedPrefs(context: Context): SharedPrefsRepo {
        return SharedPrefsRepo(context)
    }

    @Provides
    @Singleton
    fun provideGetServerStatus(api: CoinGeckoAPI, ucRepoProperties: UCRepoProperties): UCUpdateServerStatus {
        return UCUpdateServerStatus(api, ucRepoProperties)
    }


}