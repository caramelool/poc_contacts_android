package com.caramelo.contacts

import android.app.Application
import com.caramelo.contacts.di.core.ApplicationInject
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        ApplicationInject.inject(this)
    }

}
