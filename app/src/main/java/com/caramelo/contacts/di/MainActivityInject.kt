package com.caramelo.contacts.di

import android.content.Context
import com.caramelo.contacts.di.core.RepositoryInject
import com.caramelo.contacts.presenter.MainPresenter
import com.caramelo.contacts.presenter.MainView

import kotlin.reflect.KProperty

class MainActivityInject {
    operator fun getValue(ref: Any, property: KProperty<*>): MainPresenter {
        val repositoryInject = RepositoryInject(ref as Context)
        return MainPresenter(ref as MainView, repositoryInject.contactRepository)
    }
}
