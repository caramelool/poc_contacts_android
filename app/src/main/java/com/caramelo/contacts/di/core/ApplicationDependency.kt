package com.caramelo.contacts.di.core

import android.app.Application
import android.content.Context

interface ApplicationDependency {
    val applicationContext: Context
}

object ApplicationInject : ApplicationDependency {

    lateinit var context: Context

    fun inject(application: Application) {
        this.context = application
    }

    override val applicationContext: Context
        get() = context
}
