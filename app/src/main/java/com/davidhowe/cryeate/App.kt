package com.davidhowe.cryeate

import android.app.Application
import android.content.Context
import com.davidhowe.cryeate.di.AppComponent
import com.davidhowe.cryeate.di.AppModule
import com.davidhowe.cryeate.di.DaggerAppComponent
import timber.log.Timber

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        initializeDagger(base!!)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeDagger(context: Context) {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this, context))
            .build()
        appComponent.inject(this)
    }
}